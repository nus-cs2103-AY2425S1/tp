---
layout: page
title: See Yang Zhi’s Project Portfolio Page
---

### Project: SpleetWaise

SpleetWaise builds on AddressBook Level 3 (AB3) - a desktop app for managing contacts, optimized for use via a Command Line Interface (CLI) while still offering the benefits of a Graphical User Interface (GUI). Designed to streamline expense tracking for students, SpleetWaise makes it easy to record and monitor both personal and shared expenses with contacts saved in the address book. With features to keep track of balances with friends, it eliminates the confusion often associated with managing shared costs, providing a clear, organised view of who has owes what. If you can type fast, SpleetWaise lets you handle your contact and expense management tasks more efficiently than traditional GUI apps, offering students a stress-free way to manage their expenses and shared balances with contacts.

Given below are my contributions to the project.

- Role: Team Lead
- Responsibilities:
  - Scheduling and tracking
    - In charge of defining, assigning, and tracking project tasks.
  - Deliverables and deadlines
    - Ensure project deliverables are done on time and in the right format.
  - Documentation
    - Responsible for the quality of various project documents.

* **New Feature**: Transaction Management Commands
  * What it does: Added commands for clearing, and deleting transactions (clearTxn, deleteTxn), providing users the ability to delete their transaction history.
  * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them.
  * Highlights: This required building a robust transaction model (TransactionModel, TransactionBook) and handling cases of transaction duplication and ID-based lookups to maintain data integrity.

* **New Feature**: Improved Input Handling and Validation
  * What it does: Enhanced input validation for addressbook and transaction related commands, ensuring that the user inputs are valid and within the expected range.
  * Justification: This feature improves the product significantly because it enhanced the inputs that user can provide for SpleetWaise, and reduced the likelihood of errors, improving the user experience.
  * Highlights: This required implementing a robust input validation system that checks for valid inputs and provides informative error messages to guide the user.

* **UI Improvements**:
  * What it does: Updated the GUI to allow labels wrapping to ensure long tags/categories are viewable by users.
  * Justification: This feature improves the product significantly because it enhances the UX of the app, making it more user-friendly.
  * Highlights: This required updating the related fxml components to change the use of `Flowpane` to `Tilepane`, and ensuring that the lables wrap consistently across both addressbook and transaction book panes.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=zoom&zA=SeeYangZhi&zR=AY2425S1-CS2103-F13-1%2Ftp%5Bmaster%5D&zACS=243.92411702995688&zS=2024-09-20&zFS=&zU=2024-11-10&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false) 
  <iframe src="https://nus-cs2103-ay2425s1.github.io/tp-dashboard/#/widget/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=zoom&zA=SeeYangZhi&zR=AY2425S1-CS2103-F13-1%2Ftp%5Bmaster%5D&zACS=243.92411702995688&zS=2024-09-20&zFS=&zU=2024-11-10&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false&chartGroupIndex=12&chartIndex=4" frameBorder="0" width="800px" height="142px"></iframe>

* **Project management**:
  * Created and set up the project group communication platforms for team collaboration, managing team related tasks.
  * Coordinated deliverables from iteration 1 to 6, overseeing feature completion and quality checks.
  * Reviewed and approved pull requests with detailed feedback to ensure code quality and maintain project standards.
  * Organized and assigned tasks related to the implementation of critical features like transaction commands and UI updates.
  * Maintained active communication with team members to track progress and ensure alignment on project goals.

* **Enhancements to existing features**:
  * Added remark field to persons (Pull requests [\#131](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/131))

* **Documentation**:
  * User Guide:
    * Added documentation for `deleteTxn` and `editTxn` commands.
    * Updated UG to clarify data file editing guidelines [\#483](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/483).
    * Removed `Remark` command from documentation to reflect feature changes [\#461](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/461).
    * Added acknowledgments and detailed sections for new commands and features [\#328](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/328).
    * Updated UG references and renamed AB3 to align with SpleetWaise [\#237](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/237).
    * Enhanced documentation for the `clearTxn` command and updated related sections [\#108](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/108).

  * Developer Guide:
    * Abstracted common methods and refactored DG documentation for clarity [\#342](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/342).
    * `SettingUp.md` documentation has been updated to reflect the new package path for running the main application class [\#261](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/261).
    * Added details for implementing equality checks for person IDs in transactions [\#122](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/122).
    * Provided manual testing guidelines for AB-related commands to aid future developers [\#198](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/198).
    * Documented the refactoring and reorganization of core components and transaction handling [\#254](https://github.com/AY2425S1-CS2103-F13-1/tp/pull/254).

* **Community** (as of 10/11/2024): 
  * [Total PR count: 66](https://github.com/AY2425S1-CS2103-F13-1/tp/pulls?q=is%3Apr+author%3ASeeYangZhi+)
  * [Total Review count: 83](https://github.com/AY2425S1-CS2103-F13-1/tp/pulls?q=is%3Apr+reviewed-by%3Aseeyangzhi)
  * [Total Issue count: 82](https://github.com/AY2425S1-CS2103-F13-1/tp/issues/created_by/SeeYangZhi)
  * Reported bugs and suggestions for other teams in the class
    * [PE-D Examples](https://github.com/SeeYangZhi/ped/issues)
