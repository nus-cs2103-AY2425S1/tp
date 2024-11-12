---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org/).
* Libraries used:
  * [JavaFX](https://openjfx.io/)
  * [Jackson](https://github.com/FasterXML/jackson)
  * [JUnit5](https://github.com/junit-team/junit5)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

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

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103-F12-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `RestaurantListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Restaurant` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103-F12-3/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a restaurant).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103-F12-3/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Restaurant` objects (which are contained in a `UniqueRestaurantList` object).
* stores the currently 'selected' `Restaurant` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Restaurant>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Restaurant` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Restaurant` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103-F12-3/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

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

* struggle to look for eateries quickly
* unable to find eateries based on location
* struggle to find eateries that accommodate dietary restrictions

**Value proposition**: quick and efficient searches for local dining options, tailored to personal preferences.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                     | I want to …​                                                        | So that I can…​                                              |
|----------|-----------------------------|---------------------------------------------------------------------|--------------------------------------------------------------|
| `* * *`  | Tourist                     | find the best local delicacies                                      | clear my itinerary                                           |
| `* * *`  | Person with Allergies       | find eateries that meet my restrictions                             | not worry about searching far and wide for every meal        |
| `* * *`  | Hungry foodie with cravings | find restaurants with the cuisine I'm craving for                   | fulfill my cravings                                          |
| `* * *`  | Newbie                      | read up on how the app works                                        | start using the app                                          |
| `* * *`  | Foodie                      | add restaurant ratings                                              | give an informed decision to other diners' experiences       |
| `* *`    | Frequent Traveler           | find reliable and high-quality restaurants in new cities            | enjoy great meals without extensive research                 |
| `* *`    | thrifty individual          | find the cheapest food                                              | survive the day                                              |
| `* *`    | Deal Seeker                 | find discounts and special offers at nearby restaurants             | enjoy eating out without overspending                        |
| `* *`    | Health-Conscious Diner      | find restaurants that serve nutritious and diet-specific meals      | maintain my healthy lifestyle while dining out               |
| `* *`    | Pet lover                   | find restaurants that allow and accommodate for pets                | enjoy my meal with my furry companion                        |
| `*`      | Person with Disabilities    | find accessible restaurants with features like ramps                | dine out comfortably and independently                       |
| `*`      | Crippled Person             | find a wheelchair-friendly restaurant                               | eat like a normal restaurant                                 |
| `*`      | Office Worker               | plan what to eat given my 1hr lunch                                 | fit lunch into my busy schedule                              |
| `*`      | Food Connoisseur            | save my favorite restaurants                                        | quickly access them later                                    |
| `*`      | Influencer                  | find an atas restaurant                                             | post them on Instagram                                       |
| `*`      | introvert                   | filter eateries based on delivery options                           | find places that offer food delivery and enjoy meals at home |
| `*`      | Food Journalist             | discover restaurants to try and write review                        | write engaging review to recommend to others                 |
| `*`      | Carouser                    | find the bars that offer happy hours                                | drink riotously                                              |
| `*`      | Chef                        | eat at my competitors' restaurants                                  | compare the quality of my food against theirs                |
| `*`      | Student                     | find student deals for food                                         | know places to eat that are affordable for me                |

### Use cases

(For all use cases below, the **Grub Address Book (GAB)** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

#### System: Grub Address Book (GAB)

**Use Case:** UC1 - Add a new restaurant

**MSS:**
1. User enters the add command.
2. GAB displays the added restaurant.
   <br> Use case ends.

**Extensions:**
* 1a. Wrong add command format.
    * 1a1. GAB shows an error message.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the right add restaurant command format is recognised.
      <br> Use case resumes at step 2.

---

**Use Case:** UC2 - List all saved restaurants

**MSS:**
1. User enters the list command.
2. GAB displays all restaurants.
   <br> Use case ends.

---

**Use Case:** UC3 - Search by restaurant name

**MSS:**
1. User enters the find command with restaurant name.
2. GAB returns a list of restaurants matching the name.
   <br> Use case ends.

**Extensions:**
* 1a. Wrong find command format.
  * 1a1. GAB responds with a command error message and requests correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the data entered is correct.
    <br> Use case resumes at step 2.
* 2a. No matches found.
  * 2a1. GAB responds that there are no matching restaurants.
    <br> Use case ends.

---

**Use Case:** UC4 - Filter by tag names

**MSS:**
1. User enters the tags command with tag name.
2. GAB returns a list of restaurants matching the tag name.
   <br> Use case ends.

**Extensions:**
* 1a. Wrong tag command format.
  * 1a1. GAB responds with a command error message and requests correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the command format entered is correct.
    <br> Use case resumes at step 2.
* 2a. No matches found.
  * 2a1. GAB responds that there are no matching restaurants.
    <br> Use case ends.

---

**Use Case:** UC5 - Filter by price label

**MSS:**
1. User enters the price command with price label.
2. GAB returns a list of restaurants within the price label.
   <br> Use case ends.

**Extensions:**
* 1a. Wrong price command format.
  * 1a1. GAB responds with a command error message and requests correct data.
  * 1a2. User enters new data.
  * Steps 1a1-1a2 are repeated until the command format entered is correct.
    <br> Use case resumes at step 2.
* 2a. No matches found.
  * 2a1. GAB responds that there are no matching restaurants.
    <br> Use case ends.

---

**Use Case:** UC6 - Update restaurant

**MSS:**
1. User requests to <u>list all restaurants (UC2)</u> or <u>search a restaurant (UC3).</u>
2. User enters the edit command with the restaurant index.
3. GAB displays the edited restaurant.
   <br> Use case ends.

**Extensions:**
* 2a. Wrong edit command format.
  * 2a1. GAB responds with wrong command format error message.
  * 2a2. User enters new data.
  * Steps 2a1-2a2 are repeated until the right edit restaurant command format is recognised.
    <br> Use case resumes at step 3.
* 2b. Multiple saved restaurants with the same name.
  * 2b1. User first <u>searches for restaurants name (UC3).</u>
  * 2b2. GAB returns search result.
  * 2b3. User identifies the index of the right restaurant to be updated.
  * Steps 2b1-2b3 are repeated until user identifies the right restaurant.
    <br> Use case resumes at step 3.

---

**Use Case:** UC7 - Adding tags

**MSS:**
1. User adds tag via edit command after <u>initiating the update command (UC6).</u>
2. GAB displays the updated restaurant.
   <br> Use case ends.

**Extensions:**
* 1a. Wrong edit command format.
    * 1a1. GAB shows an error message.
    * 1a2. User enters new data.
    * Steps 1a1-1a2 are repeated until the right edit restaurant command format is recognised.
      Use case resumes at step 2.

---

**Use Case:** UC8 - Favourite a Restaurant

**MSS:**
1. User requests to <u>list all restaurants (UC2)</u> or <u>search a restaurant (UC3).</u>
2. User enters the favourite command with the restaurant index.
3. GAB marks the selected restaurant as a favourite.
   <br> Use case ends.

**Extensions:**
* 2a. Wrong favourite command format.
    * 2a1. GAB responds with wrong command format error message.
    * 2a2. User enters new data.
    * Steps 2a1-2a2 are repeated until the right favourite command format is recognised.
      <br> Use case resumes at step 3.
* 2b. Restaurant is already marked as favourite.
    * 2b1. GAB responds that the restaurant is already marked as favourite.
    * 2b2. User enters new data.
    * Steps 2b1-2b2 are repeated until the right restaurant is indicated.
      <br> Use case resumes at step 3.

---

**Use Case:** UC9 - Un-favourite a Restaurant

**MSS:**
1. User requests to <u>list all restaurants (UC2)</u> or <u>search a restaurant (UC3).</u> 
2. User enters the un-favourite command with the restaurant index.
3. GAB remove the selected restaurant from favourites.
   <br> Use case ends.

**Extensions:**
* 2a. Wrong un-favourite command format.
    * 2a1. GAB responds with wrong command format error message.
    * 2a2. User enters new data.
    * Steps 2a1-2a2 are repeated until the right un-favourite command format is recognised.
      <br> Use case resumes at step 3.
* 2b. Restaurant was not previously marked as favourite.
    * 2b1. GAB responds that the restaurant was not previously marked as favourite.
    * 2b2. User enters new data.
    * Steps 2b1-2b2 are repeated until the right restaurant is indicated.
      <br> Use case resumes at step 3.

---

**Use Case:** UC10 - Rate a restaurant

**MSS:**
1. User requests to <u>list all restaurants (UC2)</u> or <u>search a restaurant (UC3).</u>
2. User enters the rate command with the restaurant index.
3. GAB adds the rating to the restaurant.
   <br> Use case ends.

**Extensions:**
* 2a. Wrong rate command format.
  * 2a1. GAB responds with wrong command format error message.
  * 2a2. User enters new data.
  * Steps 2a1-2a2 are repeated until the right rate command format is recognised
    <br> Use case resumes at step 3.

---

**Use Case:** UC11 - Getting Help

**MSS:**
1. User enters a command for help.
2. GAB displays a window with a link to the user guide.
   <br> Use case ends.

---

### Non-Functional Requirements

1. **Compatibility Requirement**: Should be compatible with any system where Java 17 and the corresponding JavaFX version is installed.
2. **Capacity Requirement**: Should be able to store up to 100 restaurants at a time.
3. **Performance Requirement**: Restaurant search results should be populated in under 500 ms.
4. **Usability Requirement**: Should be easy to use any part of the app for users who can type fast and prefer typing over other interfaces.
5. **Maintainability Requirement**: Codebase should be modular and easy to maintain until the end of this iteration of CS2103.
6. **Persistency Requirement**: Restaurant entries should be persisted between sessions.
7. **Security Requirement**: Input sanitization should be implemented to prevent database corruption.
8. **Documentation Requirement**: Code should be well documented, both in-code (with JavaDocs) and otherwise (GitHub Pages, README).
9. **Robustness Requirement**: The app should handle errors such as network failures or incorrect user inputs, with clear error messages displayed in case of issues.
10. **Testability Requirement**: The codebase should have sufficient unit test coverage and integrate with CI tools to automatically run tests on every code change.

### Glossary

* **Allergy-safe**:
  If a user filters using the keyword "Allergies," it is implied that restaurants with the associated tag can serve food that is completely free of any potential allergens. This typically means dishes will consist mainly of rice and vegetables.

* **Cuisine**:
  A label used to categorize different restaurants based on the style of cooking and culinary traditions associated with a specific region or culture.

* **Affordable Price Label (`$`)**:
  Expected price per meal per restaurant to be \( x <= \$10 \). This is a subjective estimate based on human input.

* **Moderate Price Label (`$$`)**:
  Expected price per meal per restaurant to be \( \$10 < x <= \$20 \). This is a subjective estimate based on human input.

* **Premium Price Label (`$$$`)**:
  Expected price per meal per restaurant to be \( \$20 < x <= \$50 \). This is a subjective estimate based on human input.

* **Very Premium Price Label (`$$$$`)**:
Expected price per meal per restaurant to be \( x > \$50 \). This is a subjective estimate based on human input.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   a. Download the jar file and copy into an empty folder

   b. Open your command interface and run the jar file with the command `java -jar Grub.jar` Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   a. Resize the window to an optimum size. Move the window to a different location. Close the window.

   b. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Deleting a restaurant

1. Deleting a restaurant while all restaurants are being shown

   a. Prerequisites: List all restaurants using the `list` command. Multiple restaurants in the list.

   b. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   c. Test case: `delete 0`<br>
      Expected: No restaurant is deleted. Error details shown in the status message. Status bar remains the same.

   d. Other incorrect delete commands to try: `delete`, `delete x` (where x is larger than the list size)<br>
      Expected: Similar to previous.

### Finding a restaurant by name

1. Find a restaurant by its saved name

   a. Prerequisites: The requested restaurant has to be currently saved.<br>
   The first test case below assumes the user has not added any more restaurants with the name Swenswen.

   b. Test case: `find swenswen`<br>
      Expected: 1 restaurant listed!. Details of the restaurant message is shown on the right hand side.

   c. Test case: `find !@#`<br>
      Expected: Message displays no restaurants found.

   d. Other incorrect find commands to try: `find`<br>
      Expected: Error details shown in the status message. Status bar remains the same.



### Filtering by tags

1. Find a restaurant by its tag names

   a. Prerequisites: The requested restaurant has to be currently saved.<br>
   The first test case below assumes the user has not added any more restaurants with the tag halal.

   b. Test case: `tags halal`<br>
      Expected: 3 restaurant listed!. Details of the restaurant message is shown on the right hand side.

   c. Test case: `tags $$$`<br>
      Expected: Message displays no restaurants found.

   d. Other incorrect filter-by-tag commands to try: `tags`<br>
      Expected: Error details shown in the status message. Status bar remains the same.


### Filtering by price

1. Find a restaurant by its price labels

   a. Prerequisites: The requested restaurant has to be currently saved.<br>
      The first test case below assumes the user has not added any more restaurants with the price label of `$`.

   b. Test case: `price $`<br>
      Expected: 3 restaurant listed!. Details of the restaurant message is shown on the right hand side.

   c. Test case: `price expensive`<br>
      Expected: Message displays no restaurants found. 

   d. Other incorrect filter-by-price commands to try: `price`<br>
      Expected: Error details shown in the status message. Status bar remains the same.


### Rating a restaurant

1. Rating a restaurant while all restaurants are being shown

    a. Prerequisites: The requested restaurant has to be currently saved.<br>

    b. Test case: `rate 1 r/2`<br>
       Expected: First contact is given a rating of `2` from the list. Left hand side displays updated rating for the restaurant.

    c. Test case: `rate 0`<br>
       Expected: No restaurant is rated. Error details shown in the status message. Status bar remains the same.

    d. Other incorrect rate commands to try: `rate`<br>
       Expected: Similar to previous.


### Favourite a restaurant

1. Favourite a restaurant while all restaurants are being shown

    a. Prerequisites: The requested restaurant has to be currently saved.<br>
      In the test case below, we assume that none of the restaurants in the list were previously marked as favourite. 

    b. Test case: `fav 2`<br>
       Expected: Second contact has its border highlighted orange and brought to the top of the list as the first index.

    c. Test case: `fav 0`<br>
       Expected: No restaurant is turned to favourite. Error details shown in the status message. Status bar remains the same.

    d. Other incorrect favourite commands to try: `fav`<br>
       Expected: Similar to previous.


### Un-favourite a restaurant

1. Un-favourite a restaurant while all restaurants are being shown

    a. Prerequisites: The requested restaurant has to be currently saved and is a favourite restaurant as shown above in the previous test.<br>
       In the test case below, we assume that the first restaurant in the list is marked as favourite as described by the previous test case mentioned above.

    b. Test case: `unfav 1`<br>
       Expected: First contact has its border un-highlighted and brought back to its original index in the list before being set as a favourite.

    c. Test case: `unfav 0`<br>
       Expected: No favourite restaurant is turned back to normal. Error details shown in the status message. Status bar remains the same.

    d. Other incorrect un-favourite commands to try: `unfav`<br>
       Expected: Similar to previous.



## **Appendix: Effort**
Overall the difficulty that was faced in the project was mainly at the start in understanding the overall structure of the source code.
Luckily the diagrams that were provided indeed helped a lot in our teams journey in understanding and developing the project.
One of the methods that helped us a lot in our speed and ability to meet all the necessary datelines were to take inspiration of some of the code
that had already been implemented. For example, the source code for the `tags` command and feature was heavily inspired by the 
already existing `find` command and feature that came with the project.<br>

Additionally, another tough lesson we learnt was to not be too over ambitious in our planned features and implementation. At the start of the project, 
while we were ambitious with excitement on what were the list of features we wanted to add, we realised that scrapping some ideas we created was the best 
strategy as at the end of the day, we had to deliver iterations within a one-week time span, thus, looking back, while unfortunate, it was the most sound strategy we could 
have adopted, which was to work on features that we could deliver by the stipulated dateline.



## **Appendix: Planned Enhancements**
Team size: 4<br>

1. **Enhance `add` command to accept non-alphanumeric values:** The current implementation for the add command is such where names and tags can only be saved as alphanumeric values. We plan to allow adding non-alphanumeric
values for names and tags in the future. E.g. `add n/L'Éclat Culinaire p/12345678 e/test@email.com a/Pasir Ris pr/$$$ t/Crème brûlée`.
2. **Allow spaces and special characters within phone numbers:** The current implementation of adding contacts with phone numbers does not allow spaces and only allows numbers. It would be more practical if
spaces and special characters are allowed for longer phone numbers with specific area and country codes. For example: `12345678` is what can be accepted currently. We plan on allowing phone numbers like: `+65 1234 5678`.
3. **Correct grammar for "1 restaurants listed" message:** Currently the string that is returned when 1 restaurant is found in any filter command is as follows: `1 restaurants listed!`, which is grammatically incorrect. We plan on 
fixing this in the future to: `1 restaurant listed!`
4. **Improve `tags` error message:** Currently tags are only allowed to be alphanumeric values, if the user were to search `tags $$$$`, the response of `0 restaurants listed!` is the output. However, a better implementation would have been 
for the source code to first check if the keywords fits the current criteria for what is allowed as tags before finding. If the conditions were not met, a more appropriate response could have been displayed, one example 
that would be a response to the above example, would be `tags should only be alphanumeric values, if you are trying to filter by price use to price command`.
5. **Improve `price` error message:** Currently price are only allowed to be filtered by a given combination of the allowed price labels, if the user were to search `price $$$$$` or `price 4`, the response of `0 restaurants listed!` is the output. However, like the previous point, 
a better implementation would have been for the source code to first check if the input were price labels before finding. If the conditions were not met, a more appropriate response could have been displayed, for example 
`Price command only accepts a combination of the following: $ $$ $$$ $$$$`.
6. **Limit phone number length in `edit` command:** Currently a user can enter a really long phone number, e.g. `120 digits` when editing an already existing contact with the `edit` command. We plan on checking the user inputs in the future for the edit command so as to prevent such 
things from happening in the future.
7. **Include address field in duplicate detection:** Currently, the app only uses the name field for restaurant duplicate detection. A more sensible and intuitive strategy would be to include the address field as well, which would lead to a more positive user experience.
8. **Handle case-insensitive duplicates:** Currently, the app does not handle case-insensitive duplicates. For example, "McDonalds" and "mcdonalds" are treated as different restaurants. It would improve the product usage experience if this would be taken into account.
