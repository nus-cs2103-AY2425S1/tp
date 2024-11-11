---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# ClientGrid Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**
#### Use of Generative AI
Generative AI tools, including ChatGPT, were heavily used for creating detailed Javadocs, commit messages, test suite creation and occasional code refactoring throughout the development of ClientGrid.

#### Mockito
[Mockito](https://site.mockito.org/) has been used to mock unit tests.

#### AB3
Aspects of the original [AB3](https://nus-cs2103-ay2425s1.github.io/tp/) has been reused in our code.

#### JavaFx
[JavaFx](https://openjfx.io/) has been used to create the UI elements.

#### Jackson
[Jackson](https://github.com/FasterXML/jackson) has been used for the Json formatted database.

#### JUnit5
[JUnit5](https://github.com/junit-team/junit5) has been used for unit testing.

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T16-2/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T16-2/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T16-2/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ClientListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T16-2/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T16-2/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T16-2/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="500"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("deletebuyer p/91234567")` API call as an example.

<puml src="diagrams/DeleteBuyerSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `deletebuyer p/91234567` Command" />

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `ClientGridParser` object which in turn creates a parser that matches the command (e.g., `DeleteBuyerCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteBuyerCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a client).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `ClientGridParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g. `AddProperty`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddPropertyCommand`) which the `ClientGridParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddBuyerCommandParser`, `AddMeetingCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T16-2/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="850" />


The `Model` component,
* stores the client book data i.e., all `Buyer` and `Seller` objects (which are contained in a `UniqueClientList` object).
* stores the property book data i.e., all `Property` objects (which are contained in a `UniquePropertyList` object).
* stores the meeting book data i.e., all `Meeting` objects (which are contained in a `UniqueMeetingList` object).
* stores the currently 'selected' `Client`, `Property`, and `Meeting` objects (e.g., results of a list command) as separate _filtered_ lists which are exposed to outsiders as unmodifiable `ObservableList<Client>`, `ObservableList<Property>`, and `ObservableList<Meeting>` respectively. These lists can be 'observed' e.g. the UI can be bound to these lists so that the UI automatically updates when the data in the lists change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components).

### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T16-2/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="850" />

The `Storage` component,
* can save client book, property book, meeting book, and user preference data in JSON format, and read them back into corresponding objects.
* inherits from `ClientBookStorage`, `PropertyBookStorage`, `MeetingBookStorage` and `UserPrefStorage`, which means `Storage` can be treated as either one (if the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`).

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some future noteworthy details on how certain features may be implemented.

### \[Proposed\] Note-taking feature

#### Proposed Implementation

The proposed note-taking mechanism is facilitated by `NoteBook`. It extends `Meeting` by allowing users to take notes during the meeting. It implements the following operation:

* `NoteBook#write()` — Appends notes to the meeting.

* These operations are exposed in the Model interface as `Model#note()`.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* real estate agent who needs to manage a significant number of clients, properties and meetings.
* real estate agents who can speak and write in English, based in Singapore and involved in residential properties.
* prefer desktop apps over other types.
* can type fast.
* prefers typing to mouse interactions.
* is reasonably comfortable using CLI apps.

**Value proposition**:


ClientGrid is an all-in-one address book tailored for English-speaking real estate agents within Singapore to efficiently manage client contacts, including buyers and sellers. It provides a streamlined way to organize client data, monitor properties, and schedule meetings all within a single app, eliminating the need to juggle multiple apps. With offline access, agents can stay productive with ClientGrid anywhere. The default language of communication of ClientGrid is English.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`


| Priority | As a …​           | I want to …​                                                        | So that I can…​                                                                                                        |
|----------|-------------------|---------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| `* * *`  | real estate agent | add a buyer to ClientGrid                                           | keep track of the contacts of potential property buyers                                                                |
| `* * *`  | real estate agent | add a seller to ClientGrid                                          | keep track of the contacts of clients interested in selling their property                                             |
| `* * *`  | real estate agent | delete a buyer from ClientGrid                                      | remove buyers who have already successfully bought a property or are no longer interested in buying a property         |
| `* * *`  | real estate agent | delete a seller from ClientGrid                                     | remove sellers who have already successfully sold their property or are no longer interested in selling their property |
| `* * *`  | real estate agent | add a property to ClientGrid                                        | keep track of my client's property details                                                                             |
| `* * *`  | real estate agent | delete a property entry from ClientGrid                             | remove entries that I no longer need                                                                                   |
| `* * *`  | real estate agent | add a meeting with my client(s) on ClientGrid                       | keep track of all my scheduled meetings in one place                                                                   |
| `* * *`  | real estate agent | delete a meeting with my client(s) on ClientGrid                    | remove meetings that have already ended or have been cancelled                                                         |
| `* * *`  | real estate agent | list information about buyers                                       | match buyers with suitable properties based on their preferences                                                       |
| `* * *`  | real estate agent | list information about sellers                                      | manage relationships and property listings efficiently                                                                 |
| `* * *`  | real estate agent | list information about all clients (buyers and sellers)             | have a comprehensive view of all clients in one place and streamline client interactions                               |
| `* * *`  | real estate agent | list information about properties                                   | quickly view properties sellers have listed for sale to match them with potential buyers                               |
| `* * *`  | real estate agent | list information about scheduled meetings                           | quickly view upcoming meetings and plan my schedule effectively                                                        |
| `* *`    | real estate agent | find specific property types in my properties list                  | keep track of the different property types                                                                             |
| `* *`    | real estate agent | find specific properties in my properties list within a price range | keep track of the different price ranges of my properties                                                              |

### Use cases

(For all use cases below, the **System** is the `ClientGrid` and the **Actor** is the `real estate agent`, unless specified otherwise)

**Use case: UC1 - List existing buyers, sellers, clients (i.e. buyers and sellers combined), properties, or meetings in ClientGrid**

MSS:
1. Real estate agent requests to view a list of buyers, sellers, clients (i.e. buyers and sellers combined), properties, or meetings.
2. ClientGrid displays the corresponding list. 

Use case ends.

Extensions:

* 1a. ClientGrid detects that there are no existing records for the specified key.

    * 1a1. ClientGrid responds by indicating that there are no existing entries for that key yet.
  
      Use case ends.

* 1b. ClientGrid detects an invalid key, multiple keys, or additional inputs beyond the valid command.

    * 1b1. ClientGrid requests for the correct data.
  
       Use case ends.

**Use case: UC2 - Add Buyer/Seller**

MSS:
1. Real estate agent requests to add a buyer/seller to ClientGrid and passes in the buyer/seller's name, phone number and email.
2. ClientGrid will add the buyer/seller with the name, phone number, and email specified by the real estate agent.
   
Use case ends.

Extensions:

* 1a. ClientGrid detects an error in the name/phone number/email format provided by the real estate agent.

    * 1a1. ClientGrid requests for the correct data.

      Use case ends.

* 2a. ClientGrid detects that the buyer/seller already exists in the client book.

    * 2a1. ClientGrid informs the real estate agent that the buyer/seller already exists in the client book and does not add the duplicate buyer/seller.

      Use case ends.

**Use case: UC3 - Filter Client**

MSS:
1. Real estate agent requests to filter the clients by entering a name prefix.
2. ClientGrid will filter and display the clients whose names start with the provided prefix.

Use case ends.

Extensions:

* 1a. ClientGrid detects an error in the name prefix provided by the real estate agent.

    * 1a1. ClientGrid requests for the correct data.

      Use case ends.

**Use case: UC4 - Delete Buyer/Seller**

Guarantees:
* If the buyer/seller was in the client book originally, it would be removed from client book with no side effects.

MSS:
1. Real estate agent requests to delete a buyer/seller and passes in the buyer/seller's phone number.
2. ClientGrid will delete the buyer/seller with the phone number provided by the real estate agent.
   
Use case ends.

Extensions:

* 1a. ClientGrid detects an error in the phone number format provided by the real estate agent.

    * 1a1. ClientGrid requests for the correct data.

      Use case ends.

**Use case: UC5 - Add a property**

MSS:

1.  Real estate agent requests to add a property to ClientGrid and passes in the property's postal code, unit number, housing type, ask price and bid price. 
2.  ClientGrid will add the property's postal code, unit number, housing type, ask price and bid price specified by the real estate agent.
    
Use case ends.

**Extensions**

* 1a. ClientGrid detects an error in the postal code/unit/type/ask/bid format provided by the real estate agent.

    * 1a1. ClientGrid requests for the correct data.

      Use case ends.

* 2a. ClientGrid detects that the property already exists in the property book.

    * 2a1. ClientGrid informs the real estate agent that the property already exists in the property book and does not add the duplicate property.

      Use case ends.

**Use case: UC6 - Filter Property**

MSS:
1. Real estate agent requests to filter the properties by entering property type and matching price bounds.
2. ClientGrid will filter and display the properties that match the property type and has a matching price within the matching price bounds.

Use case ends.

Extensions:

* 1a. ClientGrid detects an error in the type/matching price format prefix provided by the real estate agent.

    * 1a1. ClientGrid requests for the correct data.

      Use case ends.
  
**Use case: UC7 - Delete Property**

Guarantees:
* If property listing was in the database originally, it would be removed from property database with no side effects.

MSS:
1. Real estate agent requests to delete a property listing based on the property’s postal code and unit number.
2. ClientGrid will delete the respective property listing and indicate success.

Use case ends.

Extensions:

* 1a. ClientGrid detects an error in the postal code or unit number format provided by the real estate agent.

    * 1a1. ClientGrid requests for the correct data.

        Use case ends.

* 1b. ClientGrid is unable to find a matching property listing entry in the database.

    * 1b1. ClientGrid informs real estate agent that the property listing does not exist in the database. 
  
        Use case ends.

**Use case: UC8 - Add Meeting**

MSS:
1. Real estate agent requests to add a meeting based on the meeting’s title and date. The real estate agent also specifies the buyer, seller, and property involved in this meeting.
2. ClientGrid will add the meeting and indicate success.
   
Use case ends.

Extensions:

* 1a. ClientGrid detects an error in the format of the meeting title, meeting date, buyer phone number, seller phone number, property type, or postal code provided by the real estate agent.

    * 1a1. ClientGrid requests for the correct data.

        Use case ends.

* 1b. ClientGrid detects there is a meeting of the same meeting title and meeting date in the meeting book.

    * 1b1. ClientGrid notifies the user of an existing meeting with the same title and date in the meeting book and prompts the user to modify either the meeting title or date before resubmitting. 
  
        Use case ends.

* 1c. ClientGrid is unable to find a matching buyer, seller, or property entry in the client book or property book.

    * 1c1. ClientGrid informs real estate agent that the buyer, seller, or property does not exist in the client book or property book.

        Use case ends.

**Use case: UC9 - Delete Meeting**

Guarantees:
* If meeting was in the meeting book originally, it would be removed from meeting book with no side effects.

MSS:
1. Real estate agent requests to delete a meeting based on the meeting’s meeting title and meeting date.
2. ClientGrid will delete the respective meeting and indicate success.
   
Use case ends.

Extensions:

* 1a. ClientGrid detects an error in the meeting title or meeting date format provided by the real estate agent.

    * 1a1. ClientGrid requests for the correct data.
  
        Use case ends.

* 1b. ClientGrid is unable to find a matching meeting entry in the meeting book.

    * 1b1. ClientGrid informs real estate agent that the meeting does not exist in the meeting book.

        Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 clients, 500 properties, and 500 meetings without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5. Should be able to handle case of corrupted file.

### Glossary

* **Clients**: Buyers or Sellers of properties the real estate agent is managing.
* **Client Book**: In-memory JSON file containing the clients stored in ClientGrid.
* **Corrupted file**: Missing file and invalid data.
* **Error Pane**: Window in which the error message is displayed in ClientGrid.
* **Meeting Book**: In-memory JSON file containing the meetings stored in ClientGrid.
* **Mainstream OS**: Windows, Linux, Unix, MacOS.
* **Matching Price**: The estimated price of the property given by the average of the property's lowest Ask price and highest Bid price.
* **Property Book**: In-memory JSON file containing the properties stored in ClientGrid.

--------------------------------------------------------------------------------------------------------------------

## **Planned Enhancements**

### Team size: 5

1. Support for names with special characters:
    * Current issue: ClientGrid originally did not fully accommodate client names with special characters, cultural identifiers, or symbols. Agents encountered difficulties when adding names with hyphens (e.g., "Anna-Marie"), cultural identifiers (e.g., "d/o"), or special symbols (e.g., "!John").
    * Example input: `addbuyer n/Anna-Marie p/91234444 e/anna@example.com` or `addseller n/Ramesh d/o Kumar p/81234444 e/ramesh@example.com`
    * Proposed change: Enable ClientGrid to accept names with special characters directly, ensuring accurate storage and retrieval.
    * Planned behavior: The system will validate and accept names containing hyphens, cultural identifiers, and symbols without errors or truncation.
    * Benefit: Enhances ClientGrid’s flexibility, allowing agents to store diverse client names precisely as entered, ensuring cultural and personal naming conventions are respected.
2. Enhanced name filtering in `filterclient` command:
    * Current issue: Previously, the filterclient command could only locate clients whose names started with the input, limiting search flexibility and potentially slowing the process for agents searching partial matches.
    * Example input: `filterclient n/bob` could only retrieve clients whose names started with "Bob" rather than finding "Bob" within any part of the name (e.g., "Jacobson").
    * Proposed change: Modify filterclient to search for the input string within any part of a client’s name, not just from the beginning.
    * Planned behavior: The command will return clients whose names contain the search string anywhere within the name, enhancing match versatility.
    * Benefit: Provides agents with more intuitive and efficient search capabilities, helping them locate clients faster and improving user experience.
3. Enable horizontal scrolling for long email addresses:
   * Current issue: Email addresses exceeding the visible character limit are truncated in the display panel, making it impossible for users to view the full address. This is problematic for cases where the email reaches the maximum length allowed (e.g., 320 characters for Gmail).
   * Example input: `addbuyer n/testingALongEmail p/91234444 e/thisisaverylonggmailaddressasapsychopathwishestobreaktheirfingerhavingalongemailaddressnohumanshouldpossiblyhavesuchalongemailaddressbutidontknowwhytheydohavesuchalongemailaddressiamrunningoutofwordstosaysoiamjustgonnaputfillerwordstokeepextendingthisthinghmmidonotreallylikepeandtypingabunchofweirdstuff@gmail.com`
   * Proposed change: Introduce horizontal scrolling within the email display panel, allowing users to scroll to view the entire email address. 
   * Planned behavior: The scroll bar will appear dynamically when the email exceeds the available space. 
   * Benefit: Improves usability and ensures all users, even those with long email addresses, can view their information fully and accurately.
4. Prevent duplicate email addresses with case insensitivity:
   * Current issue: It is possible for two different buyers with distinct phone numbers to be assigned the same email address if the email is typed in different cases (e.g., EXAMPLE@gmail.com and example@gmail.com). This leads to inconsistencies and potential data conflicts.
   * Example input:
     * `addbuyer n/John Doe p/91112222 e/EXAMPLE@gmail.com`
     * `addbuyer n/John Doe p/92223333 e/example@gmail.com`
   * Current behavior: Both buyers are added successfully, despite having the same email in different cases.
   * Proposed change: Implement case-insensitive validation for email addresses during buyer creation or modification. If a duplicate email (regardless of case) is detected, an error message will inform the user and prevent the action.
   * Planned behavior: The second command will fail with an error message such as: `Error: The email "example@gmail.com" is already in use.`
   * Benefit: Ensures email address uniqueness across the application, eliminating potential conflicts and improving data integrity.
5. ClientGrid will make `Ask` and `Bid` prices optional in the `filterproperty` command:
   * Current issue: Agent may not have found a suitable buyer and hence, may not have details of the `Bid` price. Likewise, the owner of the property may not have stated the selling price and hence, agent may not have `Ask` price.
   * Proposed change: `addproperty c/POSTAL_CODE u/UNIT_NUMBER t/TYPE [a/ASK] [b/BID]`
   * Planned behavior: `addproperty` will make `Ask` and `Bid` parameters options (as denoted by `[]`).
   * Benefit: Agents will be able to add properties based on the most recent `Ask` and `Bid` prices available to them.
6. ClientGrid will allow decimal numbers in `Ask` and `Bid`:
    * Current issue: `Ask` and `Bid` prices only takes numbers in increments of 1 thousand but a sizeable number of agents require pricing precision to be in the hundreds as well. 
    * Proposed change: `Ask` and `Bid` prices will include up to 3 decimal points.
    * Benefit: `Ask` and `Bid` prices better reflects their needs in price precision without overburdening them with too much details.
7. Unit number will allow letters for landed properties who share the same postal code but have different but owned by different people:
    * Current issue: There exists certain `LANDED` properties such as the semi-detached types that share the same postal code but have their unit numbers with letters to distinguish them. 
    * Proposed change: Allow `LANDED` properties to have different unit numbers with their maximum level number (digits on the left hand side of the `-` delimiter) limited to `01` because these properties expand horizontally.
    * Planned behavior: `LANDED` properties will not default to `00-00` and will only produce an error if the number on the left hand side of delimiter is not `01`. Comparison of properties with `LANDED` will also include unit numbers as well.
    * Benefit: Agents will have greater flexibility in distinguishing between `LANDED`properties.
8. Make the error pane's size adjustable
   * Current issue: Currently, the error pane is not adjustable, making it inconvenient for users to view long error messages.
   * Proposed change: Allow the error pane to be resizable so users can enlarge the error pane to view long error messages more conveniently.
   * Benefit: Making the error pane's size resizable will allow users to view the error messages in its entirety.
9. Meeting titles will allow for special characters:
   * Current issue: Meeting titles only allow for alphanumeric characters and spaces.
   * Proposed change: Allow meeting title to contain special characters.
   * Benefit: Agents will have greater flexibility in defining meeting titles.
10. Display relevant clients, buyers, properties upon calling command:
    * Current issue: Calling a command does not result in the relevant list eg. clients, buyers, sellers, or properties list being displayed to the user, resulting in the inner-workings of the app (eg. clearing of filters) being exposed to the user.
    * Proposed change: Calling a command will result in the relevant list eg. clients, buyers, sellers, or properties list to be displayed to the user: For example, calling `addmeeting mt/Meeting 1 d/01-01-2025 bp/95352563 sp/87652533 t/HDB c/123456` will cause the list of meetings to be displayed.
    * Benefit: Hide the inner-workings of the app to prevent confusing the user.

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch
   1. Download the `.jar` file and copy into an empty folder.
   2. Open a command terminal.
   3. In the command terminal, `cd` into the folder you placed the jar file in.
   2. Type the `java -jar clientGrid.jar` command to run the application.<br>Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.
2. Saving window preferences
   1. Resize the window to an optimum size. Move the window to a different location. Close the window.
   2. Re-launch the app by double-clicking the `.jar` file.<br>
       Expected: The most recent window size and location is retained.
3. Executing commands
   1. Refer to the User Guide [here](https://ay2425s1-cs2103t-t16-2.github.io/tp/UserGuide.html#command-summary) for the list of commands.
4. Closing the application
   1. Use the `exit` command to close the application.

