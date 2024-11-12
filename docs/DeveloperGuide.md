---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

<h1 id="internbuddy-developer-guide">
    <img src="images/InternBuddyLogo.png" alt="Logo" width="40" height="40" style="vertical-align:middle;">
    InternBuddy Developer Guide
</h1>

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
* The project simulates an ongoing software project for a desktop application (called _AddressBook_) used for managing contact details.
  * It is **written in OOP fashion**. It provides a **reasonably well-written** code base **bigger** (around 6 KLoC) than what students usually write in beginner-level SE modules, without being overwhelmingly big.
  * It comes with a **reasonable level of user and developer documentation**.
* It is named `AddressBook Level 3` (`AB3` for short) because it was initially created as a part of a series of `AddressBook` projects (`Level 1`, `Level 2`, `Level 3` ...).
* For the detailed documentation of this project, see the **[Address Book Product Website](https://se-education.org/addressbook-level3)**.
* This project is a **part of the se-education.org** initiative. If you would like to contribute code to this project, see [se-education.org](https://se-education.org/#contributing-to-se-edu) for more info.
* Certain sections of our [UserGuide](https://ay2425s1-cs2103t-t09-1.github.io/tp/UserGuide.html) are heavily inspired by a [past year group's UserGuide (AY2324S1-CS2103T-T11-1)](https://ay2324s1-cs2103t-t11-1.github.io/tp/UserGuide.html). In particular,
the sections `Welcome to InternBuddy!`, `Overview`, `How to use our User Guide`, and `GUI Overview` takes heavy inspiration from the aforementioned group.

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

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-T09-1/tp/tree/master/src/main/java/seedu/internbuddy/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-T09-1/tp/tree/master/src/main/java/seedu/internbuddy/MainApp.java)) is in charge of the app launch and shut down.
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

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-T09-1/tp/tree/master/src/main/java/seedu/internbuddy/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `CompanyListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-T09-1/tp/tree/master/src/main/java/seedu/internbuddy/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-T09-1/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Company` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-T09-1/tp/tree/master/src/main/java/seedu/internbuddy/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a company).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-T09-1/tp/tree/master/src/main/java/seedu/internbuddy/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Company` objects (which are contained in a `UniqueCompanyList` object).
* stores the currently 'selected' `Company` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Company>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Company` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Company` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-T09-1/tp/tree/master/src/main/java/seedu/internbuddy/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.internbuddy.commons` package.

[back to top](#internbuddy-developer-guide)

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Apply feature

#### Implementation

The `apply` command is responsible for adding a new application to a specified company. It is invoked when a user applies for a position at a company and wants to track the application in the system. This command operates on the `Model`, updating the target company by appending a new application to its list of applications.

The following methods and operations are involved:

* `ApplyCommand#execute(Model model)` — Adds a new `Application` to the specified `Company`.
* `Model#setCompany(Company targetCompany, Company updatedCompany)` — Replaces the existing company in the model with an updated one, which contains the new application.
* `AddressBook#setCompany(Company target, Company editedCompany)` — Commits the changes to the address book.

##### Example usage scenario:

Step 1. The user selects a company and executes the `apply` command with relevant application details (e.g., position name and description). The command fetches the selected company and creates a new `Application`.

Step 2. A copy of the company's current list of applications is made, and the new `Application` is added to the list.

Step 3. The `ApplyCommand` creates an updated `Company` object containing the newly added application and calls `Model#setCompany(companyToEdit, editedCompany)` to replace the old company in the model with the updated one.

Step 4. The changes are committed to the address book by calling `AddressBook#setCompany(companyToEdit, editedCompany)`.

<puml src="diagrams/ApplyCommandSequence.puml" alt="ApplyCommandSequence" />

Below displays an activity diagram that explains roughly what happens when a user tries to add an application:
<puml src="diagrams/ApplyCommandActivity.puml" alt="ApplyCommandActivity" />

#### Design considerations:

**Aspect: How to store the applications:**

* **Alternative 1 (current choice):** Store applications as a modifiable list within each `Company`.
  * Pros: Simple to implement and modify the list of applications.
  * Cons: The list must be copied to ensure immutability during updates.

* **Alternative 2:** Store applications as a separate entity with references to companies.
  * Pros: Improves separation of concerns and scalability for large datasets.
  * Cons: Increases complexity of application updates.

--------------------------------------------------------------------------------------------------------------------

### Update feature

#### Implementation

The `update` command is responsible for updating the status of an existing application for a specific company. When a user wants to modify the application status (e.g., changing from "APPLIED" to "INTERVIEWED"), this command is invoked.

The following methods and operations are involved:

* `UpdateCommand#execute(Model model)` — Updates the status of the specified `Application` for a particular `Company`.
* `Model#setCompany(Company targetCompany, Company updatedCompany)` — Replaces the old company in the model with the updated company containing the modified application.
* `AddressBook#setCompany(Company target, Company editedCompany)` — Commits the changes to the address book.

##### Example usage scenario:

Step 1. The user selects a company and executes the `update` command to change the status of an application. The command fetches the target company and retrieves the application to update.

Step 2. A copy of the company's current list of applications is made, and the relevant application is modified by updating its `AppStatus`.

Step 3. The `UpdateCommand` creates an updated `Company` object containing the modified application list and calls `Model#setCompany(companyToEdit, editedCompany)` to replace the old company with the updated one.

Step 4. The changes are committed to the address book using `AddressBook#setCompany(companyToEdit, editedCompany)`.

<puml src="diagrams/UpdateCommandSequence.puml" alt="UpdateCommandSequence" />

Below displays an activity diagram that explains roughly what happens when a user tries to update an application:
<puml src="diagrams/UpdateCommandActivity.puml" alt="UpdateCommandActivity" />

#### Design considerations:

**Aspect: How to modify the application status:**

* **Alternative 1 (current choice):** Modify the application within the company’s application list directly.
  * Pros: Easy to implement, minimal changes required in the data structure.
  * Cons: The list must be copied to maintain immutability during updates.

* **Alternative 2:** Store applications in a separate collection and update them independently of the company.
  * Pros: Cleaner separation of concerns, potentially more scalable.
  * Cons: Increased complexity in ensuring consistency between companies and applications.

--------------------------------------------------------------------------------------------------------------------

### Withdraw feature

#### Implementation

The `withdraw` command is responsible for removing an application from a specified company. It is invoked when a user wants to close an application to a company and remove it from the application list. This command operates on the `Model`, updating the target company by removing the application from its list of applications.

The following methods and operations are involved:

* `WithdrawCommand#execute(Model model)` — Removes an `Application` from the specified `Company`.
* `Model#setCompany(Company targetCompany, Company updatedCompany)` — Replaces the existing company in the model with an updated one, which does not contains the chosen application.
* `AddressBook#setCompany(Company target, Company editedCompany)` — Commits the changes to the address book.

##### Example usage scenario:

Step 1. The user selects a company and application and executes the `withdraw` command. The command fetches the selected company and identifies the `Application` to be removed.

Step 2. A copy of the company's current list of applications is made, and the selected `Application` is removed from the list.

Step 3. The `WithdrawCommand` creates an updated `Company` object containing the updated list of applications and calls `Model#setCompany(companyToEdit, editedCompany)` to replace the old company in the model with the updated one.

Step 4. The changes are committed to the address book by calling `AddressBook#setCompany(companyToEdit, editedCompany)`.

<puml src="diagrams/WithdrawCommandSequence.puml" alt="WithdrawCommandSequence" />

Below displays an activity diagram that explains roughly what happens when a user tries to withdraw an application:
<puml src="diagrams/WithdrawCommandActivity.puml" alt="WithdrawCommandActivity" />

#### Design considerations:

**Aspect: How to store the applications:**

* **Alternative 1 (current choice):** Store applications as a modifiable list within each `Company`.
  * Pros: Simple to implement and modify the list of applications.
  * Cons: The list must be copied to ensure immutability during updates.

* **Alternative 2:** Store applications as a separate entity with references to companies.
  * Pros: Improves separation of concerns and scalability for large datasets.
  * Cons: Increases complexity of application updates.

--------------------------------------------------------------------------------------------------------------------

### Update feature

#### Implementation

The `update` command is responsible for updating the status of an existing application for a specific company. When a user wants to modify the application status (e.g., changing from "APPLIED" to "INTERVIEWED"), this command is invoked.

The following methods and operations are involved:

* `UpdateCommand#execute(Model model)` — Updates the status of the specified `Application` for a particular `Company`.
* `Model#setCompany(Company targetCompany, Company updatedCompany)` — Replaces the old company in the model with the updated company containing the modified application.
* `AddressBook#setCompany(Company target, Company editedCompany)` — Commits the changes to the address book.

##### Example usage scenario:

Step 1. The user selects a company and executes the `update` command to change the status of an application. The command fetches the target company and retrieves the application to update.

Step 2. A copy of the company's current list of applications is made, and the relevant application is modified by updating its `AppStatus`.

Step 3. The `UpdateCommand` creates an updated `Company` object containing the modified application list and calls `Model#setCompany(companyToEdit, editedCompany)` to replace the old company with the updated one.

Step 4. The changes are committed to the address book using `AddressBook#setCompany(companyToEdit, editedCompany)`.

<puml src="diagrams/UpdateCommandSequence.puml" alt="UpdateCommandSequence" />

Below displays an activity diagram that explains roughly what happens when a user tries to add an application:
<puml src="diagrams/UpdateCommandActivity.puml" alt="UpdateCommandActivity" />

#### Design considerations:

**Aspect: How to modify the application status:**

* **Alternative 1 (current choice):** Modify the application within the company’s application list directly.
  * Pros: Easy to implement, minimal changes required in the data structure.
  * Cons: The list must be copied to maintain immutability during updates.

* **Alternative 2:** Store applications in a separate collection and update them independently of the company.
  * Pros: Cleaner separation of concerns, potentially more scalable.
  * Cons: Increased complexity in ensuring consistency between companies and applications.

--------------------------------------------------------------------------------------------------------------------

### Find feature

#### Implementation

The `find` command is responsible for filtering companies based on specific keywords, to be typed in after the `find` keyword. The name, application and tags of every company will be searched through and the company will be displayed in a list if the keyword is present.

The following methods and operations are involved:

* `FindCommand#execute(Model model)` - Displays companies filtered by specific search terms.
* `NameContainsKeywordsPredicate#test(Company company)` - Checks the company's name, application name, application details and tags for a match against the stream of keywords.

##### Example usage scenario:

Step 1. The user inputs several search keywords after the `find` keyword.

Step 2. The model applies the predicate to the list of companies, to create a list of filtered companies.

Step 3. The GUI displays the list of companies filtered by the given search terms.

<puml src="diagrams/FindSequenceDiagram.puml" alt="FindSequenceDiagram" />

#### Design considerations:

**Aspect: How to find applications based on keywords:**

* **Alternative 1 (current choice):** Expand on initial feature that filters companies by name.
    * Pros: Simple to expand upon, and one iteration of company list is sufficient.
    * Cons: Searching logic may increase in complexity.

* **Alternative 2:** Create new predicates for each field to be searched through.
    * Pros: Improves separation of concerns between different fields to be searched through.
    * Cons: Multiple iterations of company list will be required, which can increase time complexity with a large list.

--------------------------------------------------------------------------------------------------------------------

### View feature

#### Implementation

The `view` command allows users to see full application details of a specified `Company`.

The following methods and operations are involved:

* `ViewCommand#execute(Model model)` &mdash; Displays the specified company along with its full details.
* `CompanyToViewPredicate(Company companyToView)` &mdash; A predicate used to show only the specified company.
* `Model#viewAppDetails(Company companyToView)` &mdash; Sets the specified company to show its full details in the company list.
* `Model#updateFiledCompanyList(Predicate predicate)` &mdash; Only show the specified company and its full details.

#### Example usage scenario:

Step 1: The user selects a company and executes the `view` command with the corresponding company list index.

Step 2: The `ViewCommand` selects a company `companyToView` and creates a predicate `p` to only show this company.

Step 3: The `ViewCommand` then calls `Model#viewAppDetails(companyToView)` and `Model#updateFilteredCompanyList(p)` which
causes the UI to display only the specified company and its full application details.

<box type="info" seamless>

**Note:** `Model#hideAppDetailsForAll()` is executed at the start of `LogicManager#execute(String commandText)`
to ensure that full application details are only shown for the `view` command.
</box>

<puml src="diagrams/ViewSequenceDiagram.puml" alt="ViewSequenceDiagram" />

#### Design considerations:

**Aspect: How to display the results of `view`:**

* **Alternative 1:** Display results in the results box.
  * Pros: Easier and simpler to implement.
  * Cons: Users edit the viewed company after using the view command. (ie: they have to manually search for it again)

* **Alternative 2 (current choice):** Show the singular company with full details in the company list.
  * Pros: Users can use other commands after using `view` to manipulate the company.
  * Cons: Harder to implement as the full application details should be hidden after running additional commands.

--------------------------------------------------------------------------------------------------------------------

### Favourite/unfavourite feature

#### Implementation

The `fav`/`unfav` command allows users to mark specified a `Company` as favourites or not. Companies that are favourited
will appear at the top of the company list at all times along with other favourited companies (provided that they are not hidden by other commands like `find`).

The `fav` command is facilitated by `FavCommand` and `FavCommandParser`. Similarly, `unfav` command is facilitated by
`UnfavCommand` and `UnfavCommandParser`. For brevity, we refer to both using `XXFavCommand` and `XXFavCommandParser`
Since they function similarly.

The following methods and operations are involved:
* `XXFavCommand#execute(Model model)` &mdash; (Un)Favourites the specified `Company`.
* `Model#setCompany(Company targetCompany, Company updatedCompany` &mdash; Replaces the existing company in the model with
the same `Company` but with its `isFavourite` set to `true` or `false` depending on `XXFavCommand`
* `AddressBook#setCompany(Company target, Company editedCompany` &mdash; Commits the changes to the address book.
* `UniqueCompanyList#setCompany(Company target, Company editedCompany)` &mdash; Updates internal list, sorting by favourited companies.

#### Example usage scenario:

Step 1. The user selects a company and executes the `XXfav` command with the corresponding company list index.

Step 2. The `XXFavCommand` fetches the selected company `companyToEdit` and creates an identical company `editedCompany` but with the updated `isFavourite` field and
calls `Model#setCompany(companyToEdit, editedCompany)` to replace the old company in the model with the updated one.

Step 3. The changes are committed to the address book by calling `AddressBook#setCompany(companyToEdit, editedCompany)`; the address book replaces
the old company in its `UniqueCompanyList` sorting by `isFavourite` in the process by calling `UniqueCompanyList#setCompany(companyToEdit, editedCompany)`.

<puml src="diagrams/FavSequenceDiagram.puml" alt="FavSequenceDiagram" />

#### Design considerations:

**Aspect: How to show favourite companies at the top of the company list:**

* **Alternative 1 (current choice):** Uniformly ensure that companies are always sorted by favourites in all
methods of `UniqueCompanyList` that manipulates its `internalList`.
   * Pros: Simple to implement and ensures that favourited companies always remain on top.
   * Cons: Some time cost is incurred for all commands that modify the company list as list is always sorted by favourites.

* **Alternative 2:** Have a specific method in `AddressBook` that sorts the companies.
   * Pros: More flexible which allows different methods of sorting the future.
   * Cons: More effort by developers to ensure that the list is sorted by favourites when it should be.

--------------------------------------------------------------------------------------------------------------------

### Reopen feature

#### Implementation

The `reopen` command allows users to change the status of a specified `Company` from `CLOSED` to `INTERESTED`

The `reopen` command is facilitated by `ReopenCommand` and `ReopenCommandParser`.

The following methods and operations are involved:
* `ReopenCommand#execute(Model model)` &mdash; Reopens the specified `Company`.
* `Model#setCompany(Company targetCompany, Company updatedCompany` &mdash; Replaces the existing company in the model with
the same `Company` but with its `Status` set to `INTERESTED`
* `AddressBook#setCompany(Company target, Company editedCompany` &mdash; Commits the changes to the address book.

#### Example usage scenario:

Step 1. The user selects a company and executes the `reopen` command with the corresponding company list index.

Step 2. The `ReopenCommand` fetches the selected company `companyToEdit` and creates an identical company `editedCompany` but with the updated `Status` field and
calls `Model#setCompany(companyToEdit, editedCompany)` to replace the old company in the model with the updated one.

Step 3. The changes are committed to the address book by calling `AddressBook#setCompany(companyToEdit, editedCompany)`


#### Design considerations:

**Aspect: How to handle reopening a company:**

* **Alternative 1 (current choice):** Change the status of the company directly from `CLOSED` to `INTERESTED`.
   * Pros: Simple to implement and straightforward for users to understand.
   * Cons: Limited flexibility if more statuses are introduced in the future.

* **Alternative 2:** Introduce an intermediate status such as `REOPENING` before changing to `INTERESTED`.
   * Pros: Provides more granularity and control over the reopening process.
   * Cons: Adds complexity to the implementation and may confuse users with additional statuses.

[back to top](#internbuddy-developer-guide)

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

* has a need to manage a significant number of contacts for potential companies for internships
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage contacts faster than a typical mouse/GUI driven app

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                                     | I want to …​                                                  | So that I can…​                                                                                          |
|----------|-------------------------------------------------------------|---------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|
| `* * *`  | new user                                                    | see usage instructions                                        | refer to instructions when I forget how to use the App                                                   |
| `* * *`  | user that types fast                                        | utilize an easy to use CLI interface                          | be more efficient while using the app                                                                    |
| `* *`    | user who prefers less screen clutter                        | toggle list view to be less detailed / more general           |                                                                                                          |
| `* *`    | user                                                        | export my data out of InternBuddy                             | have a data backup and/or share company information with fellow peers                                    |
| `* * *`  | STEM major planning to apply for internships                | add a company to the list                                     | keep track of companies I am interested in                                                               |
| `* *`    | STEM major planning to apply for internships                | find a specific company by name                               | retrieve information about a company without having to go through the entire list                        |
| `* *`    | STEM major planning to apply for internships                | find specific companies based on tags                         | retrieve information on companies attached to a certain tag without having to go through the entire list |
| `* *`    | STEM major planning to apply for internships                | find specific companies based on application names or details | retrieve information about companies without having to go through the entire list                        |
| `* *`    | STEM major planning to apply for internships                | add additional info about the company                         | track the relevant information pertaining to the company                                                 |
| `* * *`  | STEM major planning to apply for internships                | update the information for a specific company                 | ensure my information is up-to-date                                                                      |
| `*`      | STEM major planning to apply for internships                | add referral contacts to companies                            | remember to include them when applying                                                                   |
| `* *`    | STEM major planning to apply for internships                | favourite a company                                           | mark that company as a priority                                                                          |
| `* *`    | STEM major planning to apply for internships                | unfavourite a company                                         | unmark that company as a priority if no longer relevant                                                  |
| `*`      | STEM major planning to apply for internships                | sort companies by hiring status                               | better prioritize certain companies                                                                      |
| `*`      | STEM major planning to apply for internships                | sort companies by location distance                           | better prioritize certain companies                                                                      |
| `*`      | STEM major planning to apply for internships                | sort companies by remote work availability                    | better prioritize certain companies                                                                      |
| `*`      | STEM major planning to apply for internships                | sort companies by working hour flexibility                    | better prioritize certain companies                                                                      |
| `*`      | STEM major planning to apply for internships                | sort companies by internship role                             | better prioritize certain companies                                                                      |
| `* * *`  | STEM major currently applying for internships               | add an application to a specific company                      | keep a log of my applications to the company                                                             |
| `* * *`  | STEM major currently applying for internships               | remove an application from a specific company                 | remove applications that are no longer relevant to the company                                           |
| `* * *`  | STEM major currently applying for internships               | update internship application status for a specific company   | maintain an updated list of the companies I'm applying for                                               |
| `* *`    | STEM major currently applying for internships               | sort companies applications by status                         | focus on companies that need immediate attention                                                         |
| `*`      | STEM major currently applying for internships               | add notes for upcoming / completed interviews                 | find a specific piece of info for that interview in the future                                           |
| `*`      | STEM major currently applying for internships               | organize interview timings                                    | ensure that they do not overlap                                                                          |
| `* * *`  | STEM major planning to apply or is applying for internships | remove companies which are no longer relevant to me           | reduce clutter in my list of potential companies                                                         |


### Use cases

(For all use cases below, the **System** is the `InternBuddy` and the **Actor** is the `user`, unless specified otherwise)

**<a id="uc01"></a>Use case: UC01 - Add a company**

**MSS**

1.  User requests to add a company.
2.  InternBuddy adds the company.

    Use case ends.

**Extensions**

* 1a. User provides incomplete required information for the company (name or email).
    * 1a1. InternBuddy shows an error message.

      Use case resumes at step 1.

* 1b. User specifies an optional company information that is empty (address, contact number, or tags).
    * 1b1. InternBuddy shows an error message.

      Use case resumes at step 1.

* 1c. User specified parameter is in wrong format.
    * 1c1. InternBuddy shows an error message.

      Use case resumes at step 1.

* 1d. User provides an already saved company.
    * 1e1. InternBuddy shows an error message.

      Use case resumes at step 1.

**<a id="uc02"></a>Use case: UC02 - List all saved companies**

**MSS**

1.  User requests to list companies.
2.  InternBuddy shows a list of companies.

    Use case ends.

**Extensions**

* 1a. The list is empty.
     * 1a1. InternBuddy shows a message to indicate that the list is empty.

       Use case ends.

**<a id="uc03"></a>Use case: UC03 - Find a company by keyword (name, tag, or application name)**

**MSS**

1.  User searches for a company using a keyword.
2.  InternBuddy shows the matching company(s).

    Use case ends.

**Extension**

* 1a. User did not provide a keyword to search.
    * 1a1. InternBuddy shows an error message.

      Use case resumes at step 1.

* 2b. No companies with matching keywords found.
    * 2b1. InternBuddy shows an error message to indicate no matches found.

      Use case ends.

**<a id="uc04"></a>Use case: UC04 - Delete a saved company**

**MSS**

1.  User requests to <ins>[list saved companies (UC02)](#uc02)</ins> or <ins>[find a company (UC03)](#uc03)</ins>.
2.  User requests to delete a specific company in the list by its index on the list.
3.  InternBuddy deletes the company.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case resumes at step 1.

* 2a. The given index is invalid.
    * 2a1. InternBuddy shows an error message.

    Use case resumes at step 2.

**<a id="uc05"></a>Use case: UC05 - Edit company information**

1.  User requests to <ins>[list saved companies (UC02)](#uc02)</ins> or <ins>[find a company (UC03)](#uc03)</ins>.
2.  User requests to edit a specific company in the list by its index on the list.
3.  InternBuddy edits the company.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case resumes at step 1.

* 2a. The given index is invalid.
    * 2a1. InternBuddy shows an error message.

  Use case resumes at step 2.

* 2c. User attempts to change the name to another existing company.
    * 2c1. InternBuddy shows an error message.

  Use case resumes at step 2.

* 2c. User provides updated information in the wrong format.
    * 2c1. InternBuddy shows an error message.

  Use case resumes at step 2.

**<a id="uc06"></a>Use case: UC06 - Adding a company to favourites**

**MSS**

1.  User requests to <ins>[list saved companies (UC02)](#uc02)</ins> or <ins>[find a company (UC03)](#uc03)</ins>.
2.  User requests to favourite a specific company in the list by its index on the list.
3.  InternBuddy adds the company to favourites.
4.  InternBuddy shows the list of companies with the specified company now at the top with other favourited companies.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case resumes at step 1.

* 2a. The given index is invalid.
    * 2a1. InternBuddy shows an error message.

  Use case resumes at step 2.

* 2b. The company is already favourited.
    * 2b1. InternBuddy shows an error message.

  Use case resumes at step 2.

**<a id="uc07"></a>Use case: UC07 - Removing a company from favourites**

**MSS**

1.  User requests to <ins>[list saved companies (UC02)](#uc02)</ins> or <ins>[find a company (UC03)](#uc03)</ins>.
2.  User requests to unfavourite a specific company in the list by its index on the list.
3.  InternBuddy removes the company from favourites.
4.  InternBuddy shows the list of companies with the specified company now being below any other favourited companies (if any).

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case resumes at step 1.

* 2a. The given index is invalid.
    * 2a1. InternBuddy shows an error message.

  Use case resumes at step 2.

* 2b. The company is already unfavourited.
    * 2b1. InternBuddy shows an error message.

  Use case resumes at step 2.

**<a id="uc08"></a>Use case: UC08 - Adding an application to a company**

**MSS**

1.  User requests to add an application to a specific company in the list by its index on the list.
2.  InternBuddy adds the application to the company.

**Extensions**

* 1a. User provides incomplete required information for the application (name or description).
    * 1a1. InternBuddy shows an error message.

      Use case resumes at step 1.

* 1b. User specifies an optional company information that is empty (application status).
    * 1b1. InternBuddy shows an error message.

      Use case resumes at step 1.

* 1c. User specified parameter is in wrong format.
    * 2c1. InternBuddy shows an error message.

      Use case resumes at step 1.

* 2a. The status of the company is not `APPLIED`.
    * 2a1. InternBuddy sets the status of the company to `APPLIED`.

      Use case ends.

**<a id="uc09"></a>Use case: UC09 - Remove an application from a company**

**MSS**

1.  User requests to <ins>[list saved companies (UC02)](#uc02)</ins> or <ins>[find a company (UC03)](#uc03)</ins>.
2.  User requests to remove an application from a specific company at a specific index from the list of applications belonging to the company; where the company is
    specified with using its index on the list of companies.
3.  InternBuddy removes the application from the company.

    Use case ends.

**Extensions**

* 1a. The list is empty.

  Use case resumes at step 1.

* 2a. The given index for the application or the company is invalid.
    * 2a1. InternBuddy shows an error message.

      Use case resumes at step 2.

* 3a. The company has no applications left after the application is removed.
    * 3a1. InternBuddy sets the status of the company to `CLOSED`.

      Use case ends.

**<a id="uc10"></a>Use case: UC10 - Edit company application status**

**MSS**

1.  User requests to <ins>[list saved companies (UC02)](#uc02)</ins> or <ins>[find a company (UC03)](#uc03)</ins>.
2.  User requests to update the status of an application from a specific company at a specific index from the list of applications belonging to the company; where the company is
    specified with using its index on the list of companies.
3.  InternBuddy edits the company.

**Extensions**

* 1a. The list is empty.

  Use case resumes at step 1.

* 2a. The given index for the application or the company is invalid.
    * 2a1. InternBuddy shows an error message.

      Use case resumes at step 2.

* 2b. User updated application status is invalid.
    * 2b1. InternBuddy shows an error message.

      Use case resumes at step 2.

**<a id="uc-11"></a>Use case: UC11 - View full application details of a company**

**MSS**

1.  User requests to <ins>[list saved companies (UC02)](#uc02)</ins> or <ins>[find a company (UC03)](#uc03)</ins>.
2.  User requests to view the full application details of a company by its given index in the list.
3.  InternBuddy shows the application details of the company.

**Extensions**

* 1a. The list is empty.

  Use case resumes at step 1.

* 2a. The given index for the application or the company is invalid.
    * 2a1. InternBuddy shows an error message.

      Use case resumes at step 2.

**<a id="uc-12"></a>Use case: UC12 - Clear all saved companies**

**MSS**

1.  User requests to clear all companies.
2.  InternBuddy deletes all saved companies.

Use case ends.

**<a id="uc-13"></a>Use case: UC13 - Requesting for help**

**MSS**

1.  User requests for help.
2.  InternBuddy displays help information to the user.

Use case ends.

**<a id="uc-14"></a>Use case: UC14 - Exiting the app**

**MSS**

1.  User requests to exit the app.
2.  InternBuddy terminates.

Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 companies without a noticeable sluggishness in performance for typical usage.
3.  Should be able to store up to 1000 companies with up to 1000 applications each.
4.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
5.  The system should respond to user commands without noticeable lag for most operations.
6.  The application should allow for seamless data export in common formats (e.g., CSV or JSON) for external backup or sharing.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **CLI**: Command Line Interface, a method of interacting with software using typed commands rather than a graphical interface.
* **CSV**: Comma Separated Values, a text file format that uses commas to separate values, and newlines to separate records.
* **JSON**: JavaScript Object Notation, a lightweight data-interchange [format](https://www.json.org/json-en.html).

[back to top](#internbuddy-developer-guide)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:**
* These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

* Tests for features involving the command box have the following format:
```
Test: <input to test>, <next input to test>
Expected: <expected result>
```

* Some tests have prerequisites before proceeding, they will be indicated by:
```
Prerequisites: <prerequisites>
```

* Ensure that you have read the [notes about command format and the various commands themselves](https://ay2425s1-cs2103t-t09-1.github.io/tp/UserGuide.html#features)
  from the User Guide, as they will not be repeated here.

</box>

### Launch and shutdown

1. Initial launch

   1. Ensure you have Java `17` or above installed in your Computer by opening up your terminal application (for macOS it's called `Terminal`, and for Windows: `Command Prompt`), followed by executing `java -version`.

   2. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T09-1/tp/releases) (scroll down to assets to find it!).

   3. Copy/move the file to the folder you want to use as the _home folder_ for your InternBuddy application.

   4. Open a command terminal, run the `cd` command to change your directory to the folder you put the jar file in.

   5. Use the `java -jar internbuddy.jar` command to run the application.<br>
      Expected: Shows the GUI with a set of sample contacts. The window size may not be optimal.

2. Saving window preferences

   1. Resize the window to an optimal size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Display help

1. Display help.

   1. Test case: `help` <br>
      Expected: The help window which redirects user to the [User Guide](https://ay2425s1-cs2103t-t09-1.github.io/tp/UserGuide.html) is shown.

### Adding a company to favourites

1. Add a company to favourites

   1. Prerequisites: The company to test is not favourited (ie: The "star" in the top-right corner of the company panel is not yellow).

   2. Test case: `fav INDEX` <br>
      Expected: The company corresponding to `INDEX` is now favourited (The "star" in the top-right corner of the company panel is yellow).
      A message showing the favourite company is displayed in the results box.

        <box type="info" seamless>

        **Note:** Favourites companies (that are not hidden) will always appear together at the top of the company list.

        </box>

   3. Try using invalid indexes: `fav 0`, `fav x` (where `x` is more than number of companies shown in the company list).
      Expected: No companies are favourited. An error message with details is displayed in the results box.

### Removing a company from favourites

The inverse of adding to favourites. The "star" in the top-right corner should change from yellow to unfilled.

### Finding a company

1. Finding a company via a keyword.

   1. Test case: `find KEYWORD` <br>
      Expected: Companies with matching KEYWORD in either their name, tags or in the position name of one of its applications are shown.
      Number of matching companies is displayed in the result box.

2. Finding companies using multiple keywords.

    1. Test case: `find KEYWORD ANOTHER_KEYWORD...` <br>
       Expected: Companies that match ANY KEYWORD in either their name, tags or in the position name of one of its applications are shown.
       Number of matching companies is displayed in the result box.

### Displaying all companies

1. Display all added companies

   1. Prerequisites: Perform a `find` command that does not return all companies.

   2. Test case: `list` <br>
      Expected: All companies are shown in the company list. A message indicated that all companies are listed is displayed in the results box.

### Adding a company

1. Add a new company to InternBuddy

   1. Prerequisites: The company to add is not already present in the company list (ie: using `list` command does not show a company with the same name).

   2. Test case: `add n/NAME e/EMAIL` <br>
      Expected: The company with the `NAME` and `EMAIL` is added to the end of the company list. Details of the company are shown in the results box. The added company also should have the status `INTERESTED` and is not favourited.

   3. Try adding using optional fields: `add n/NAME e/EMAIL p/PHONE_NUMBER`, `add n/NAME e/EMAIL t/TAG t/OTHER_TAG`, `...` <br>
      Expected: Similar to previous test, except now it shows the other optional fields added.

   4. Try invalid command formats: `add`, `add n/NAME e/INVALID_EMAIL`, `...` <br>
      Expected: No company is added. Error message indicating invalid format is shown in the result box.

2. Add an already existing company to InternBuddy

   1. Prerequisites: The added company to test already exists in the company list (ie: you can find the company with the same name from the company list after using `list`).

   2. Test case: `add n/SAME_NAME e/EMAIL` (where `SAME_NAME` is the name of another exiting company) <br>
      Expected: No company is added. Error message indicating the company is already present is shown in the result box.

3. Add a company without some required fields

   1. Test case: `add n/NAME p/PHONE_NUMBER`, `add e/EMAIL p/PHONE_NUMBER` <br>
      Expected: No company is added. Error message with details is shown in the result box.

### Editing a company

1. Edit a company

   1. Prerequisites: List all companies using the `list` command. Multiple companies in the list.<br>

   2. Test case: `edit INDEX n/NAME` (where `NAME` is not the name of an existing company) <br>
      Expected: The company at the corresponding INDEX has its name changed to `NAME`. A message containing edit details is displayed in the result box.

   3. Test case: `edit INDEX n/SAME_NAME` (where `SAME_NAME` is the name of another exiting company) <br>
      Expected: The company is not edited. Error message indicating the edited name already exists is shown in the results box.

   4. Test case: `edit INDEX p/PHONE_NUMER e/EMAIL` <br>
      Expected: Similar to above test case, except that phone number and email are changed to `PHONE_NUMBER` and `EMAIL` respectively.

   5. Test case: `edit INDEX t/` <br>
      Expected: The company at the corresponding INDEX has its tags removed. A messaged containing edit details is displayed in the result box.

   6. Try invalid command formats: `edit`, `edit 0 n/NAME`, `edit x e/EMAIL`, `edit INDEX p/INVALID_PHONE_NUM`, `...` (where `x` is larger than the list size) <br>
      Expected: No edits are done. Error message with details is shown in the result box.

### Deleting a company

1. Deleting a company while all companies are being shown

   1. Prerequisites: List all companies using the `list` command. Multiple companies in the list.

   2. Test case: `delete 1`<br>
      Expected: First company is deleted from the company list. Details of the deleted contact shown in the results box.

   3. Test case: `delete 0`<br>
      Expected: No company is deleted. Error message indicating invalid format is shown in the results box.

   4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where `x` is larger than the list size)<br>
      Expected: Similar to previous.

2. Deleting a company while some companies are hidden

   1. Prerequisites: Some companies must be hidden (ie: after using `find` command), but not all.

   2. Test case: `delete 1` <br>
      Expected: First contact is deleted from the currently shown list. After running the `list` command again this company should no longer be present.

### Adding applications to a company

1. Adding applications to a company with no existing applications

   1. Prerequisites: The company corresponding to `INDEX` has no applications.

   2. Test case: `apply INDEX n/NAME d/DESCRIPTION` <br>
      Expected: An application with the `NAME` that has application status `APPLIED` is added to the corresponding company.
      The company's status changes to `APPLIED`. A message displaying the created application is shown in the results box.

   3. Try adding an application with different application status: `apply INDEX n/NAME d/DESCRIPTION as/APP_STATUS` <br>
      Expected: Similar to above except the application of the status of the added application should be `APP_STATUS`.

   4. Try invalid command formats: `apply`, `apply 0 n/NAME d/DESCRIPTION`, `apply INDEX n/NAME d/DESCRIPTION as/INVALID_APP_STATUS`, `...` <br>
      Expected: No application is added. Error message with details is shown in the result box.

2. Adding applications to a company with existing applications

    1. Prerequisites: The company corresponding to `INDEX` has existing applications.

    2. Test case: `apply INDEX n/NAME d/DESCRIPTION` <br>
       Expected: A new application is added to the corresponding company after all its existing applications. A message
       displaying the created application is shown in the results box.

### Editing application status

1. Edit the status of an application

   1. Test case: `update c/COMPANY_INDEX app/APPLICATION_INDEX as/APP_STATUS` <br>
      Expected: The application corresponding to `APPLICATION_INDEX` for the company at `COMPANY_INDEX` of the list has its application status changed to `APP_STATUS`.

   2. Try invalid command formats: `update`, `update COMPANY_INDEX app/APPLICATION_INDEX as/APP_STATUS`, `update c/COMPANY_INDEX app/APPLICATION_INDEX as/INVALID_APP_STATUS` <br>
      Expected: No application is updated. An error message with details is displayed in the results box.

### Removing application from a company

1. Remove an application from a company with multiple applications

   1. Prerequisites: The company corresponding to `COMPANY_INDEX` has multiple applications.

   2. Test case: `withdraw c/COMPANY_INDEX app/APP_INDEX` <br>
      Expected: The application corresponding to `APP_INDEX` for the company at `COMPANY_INDEX` is removed. A message containing the details of the removed application is displayed in the results box.

   3. Try invalid fields: `withdraw`, `withdraw COMPANY_INDEX app/APP_INDEX`, `withdraw c/INVALID_INDEX app/APP_INDEX`, `...` <br>
      Expected: No application is removed. An error message with details should be displayed in the results box.

2. Remove an application from a company with only one application

    1. Prerequisites: The company corresponding to `COMPANY_INDEX` has only one application.

    2. Test case: `withdraw c/COMPANY_INDEX app/APP_INDEX` <br>
       Expected: The application corresponding to `APP_INDEX` for the company at `COMPANY_INDEX` is removed.
       The application status of the company is changed to `CLOSED`. A message containing the details of the removed application is displayed in the results box.

### Viewing full application details of a company

1. View the full application details of a company

   1. Requisites: The company corresponding to `INDEX` has at least one application.

   2. Test case: `view INDEX` <br>
      Expected: Only the selected company is shown in the company list. The `DESCRIPTION` of all the applications for the
      selected company are fully visible. A message displaying the name of the company to view is shown.
   
   3. Try invalid fields: `view`, `view 0`, `view x`, `...` (where `x` is larger than the list size) <br>
      Expected: No application details of any companies are shown, company list remains unchanged. An error message with details should be displayed in the results box.

### Reopening a company

1. Reopen a company that has a status of `CLOSED`

   1. Prerequisites: The company corresponding to `INDEX` has a status of `CLOSED`.

   2. Test case: `reopen INDEX` <br>
      Expected: The company corresponding to `INDEX` status changes from `CLOSED` to `INTERESTED`. A message displaying the
      reopened company is displayed in the results box.

   3. Try invalid fields: `reopen`, `reopen INVALID_INDEX`, `reopen x`, `reopen y`, `...` (where `x` is larger than the list size and `y` is an index corresponding to company that is not `CLOSED`) <br>
      Expected: No company is reopened. An error message with details should be displayed in the results box.


### Saving data

<box type="info" seamless>

**Note:**
* Data is written to the file specified in `preferences.json` in the same directory that the JAR file for InternBuddy is located.
  It is specified by the `addressBookFilePath` field. <br> By default, `preferences.json` has the following `addressBookFilePath`:
```json
"addressBookFilePath": "data\\addressbook.json"
```
* Whenever a user uses a command that manipulates the stored companies directly (`add`, `delete`, `edit`, etc.), the resultant
  companies will be saved back to the file as specified by the `addressBookFilePath` field in `preferences.json`.
</box>

#### Testing saving functionality

1. Move the JAR file to a fresh directory. Run and close InternBuddy to reset `preferences.json` to its default state.

2. Run a few commands that manipulates the stored companies (`add`, `delete`, `edit`, `apply`, etc.)

3. Exit InternBuddy using `exit`

4. Verify that the data is correctly saved in `data/addressbook.json` in the same directory as the JAR file.

#### Handling erroneous data files

1. Move the JAR file to a fresh directory. Run and close InternBuddy to reset `preferences.json` to its default state.

2. Test handling of missing data file

   1. Delete `data/addressbook.json`.

   2. Relaunch InternBuddy.

   3. Expected: A new `data/addressbook.json` file should be created, and it should be filled with sample data.

3. Test handling of corrupted data file

   1. Create a new file `data/corrupted.json` that has the same data as `data/addressbook.json` but edited by removing or adding lines
      such that it does not match the valid JSON format required for the data file.

   2. Update `preferences.json` to contain:
      ```json
      "addressBookFilePath": "data\\corrupted.json"
      ```

   3. Relaunch InternBuddy

   4. Expected: InternBuddy will launch with an empty company list. The app will overwite the corrupted data
      and replace it with an empty data file:
      ```json
      {
      "companies" : [ ]
      }
      ```

[back to top](#internbuddy-developer-guide)

## **Appendix: Planned Enhancements**

Team size: 5

1. **Make `NAME` field less restrictive**: Currently, only alphanumeric characters are permitted, which means names like "Ernst & Young" and "UI/UX developer" cannot be accurately represented. We plan to expand the set of special characters allowed in the `NAME` field to support a wider range of company names and applications.

2. **Fix inconsistencies and improve clarity of error messages**: Currently, the `withdraw` command gives an error message of "Index is not a non-zero unsigned integer." when an incorrect company or application index is supplied. 
We plan to make this message clearer by changing it to "Index is not a positive integer". When an empty string is passed into the text field, the current error message is "Invalid command format!", this can be made clearer by displaying "Please input a command" instead.
For any invalid or unknown commands, a suggestion to view help will also be displayed to improve user experience. When "0" is passed as an index, the currently displayed generic error message "Invalid command format" will also be modified to "Please provide a valid positive integer as company index!" for improved clarity.
When an excessively large number is passed as an index, the currently displayed generic error message "Invalid command format" will also be modified to "Index is too large! Should not exceed list size.", with the actual list size specified for improved clarity.

3. **Improved `edit` command**:

   1. Currently, the `edit` command does not check if the user is trying to do a redundant edit (eg: changing the email to the same email), which may result in error-prone users (when trying do a minor update like `PHONE: 98765432 -> 98675432`) mistakenly thinking that they have edited the selected company correctly.

   2. After each edit operation is executed successfully, the application view returns to the full list of companies view (regardless of any existing filtered view by `find` command). This inconveniences users who are aiming to execute consecutive updates to the same company that has been filtered using the `find` command. We plan to fix this by <ins>1) preventing users from doing redundant edits</ins> and <ins>2) keeping the existing filtered list view instead of automatically returning to the full list of companies view after each successful edit operation</ins>.

4. **Support for country codes in the `PHONE_NUMBER` field**: Currently, only numeric phone numbers without country codes are accepted, making it difficult to record numbers for international companies. We plan to enhance the AddressBook to accommodate country codes, enabling support for a broader range of company phone numbers.

5. **Ability to remove optional fields for companies**: Currently, once an optional field is added to a company, it cannot be removed. We plan to enhance the `edit` command to allow users to clear optional fields, providing greater flexibility in managing company information.

6. **Better handling of extreme inputs**: Currently, when an input field is excessively long, it can lead to faulty behaviour or incorrect error messages being displayed.
Phone numbers will be capped at 15 digits as per the international limit, tags will be truncated to prevent interference with the GUI layout.

7. **Duplicate handling**: Currently, companies can have the same fields such as `PHONE_NUMBER`, `ADDRESS`, and `EMAIL`. We plan to detect this duplication and display warnings to the user when they are adding/editing these fields in a company.

8. **Better handling of extreme inputs**:

   1. Currently, when an input field is excessively long, it can lead to faulty behaviour or incorrect error messages being displayed.

   2. Phone numbers will be capped at 15 digits as per the international limit, tags will be truncated to prevent interference with the GUI layout.

9. **New `close` command**: Currently, the only way to set the status of a company from "INTERESTED" to "CLOSED" is when all applications are withdrawn or closed.
This can inconvenience users who decide not to consider a company under certain circumstances or after reopening it, and we will fix this by creating a `close` command to allow users to set company status to "CLOSED" for greater control and flexibility.
