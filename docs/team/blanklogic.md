---
  layout: default.md
  title: "Jaymeson Koh's Project Portfolio Page"
---

### Project: InternBuddy

**InternBuddy** is a desktop application developed for STEM university students to help manage and streamline their
internship application process. It enables students to track various internship opportunities across multiple companies, keeping crucial information organized and accessible. InternBuddy features both CLI and GUI elements, using JavaFX for the interface. It is written in Java and consists of around 15 kLoC.

Given below are my contributions to the project.

---

* **New Feature**: Implemented the `apply` command to manage internship applications within saved company profiles.
  * **What it does**: This feature enables users to add specific internship application details to companies already stored in the application. Users can include the role name, a description, and current application status (e.g., APPLIED, OFFERED).
  * **Justification**: The `apply` command improves the product’s utility by providing users with a method to manage each step of their internship application process with greater detail, all within the same application.
  * **Highlights**: This feature required careful consideration of data handling to ensure company information and application details remain consistent. Additionally, it was implemented with flexibility in mind, supporting multiple status types for each application.
  * **Credits**: Some design inspirations were adapted from AddressBook-Level 3, with significant adjustments for application-specific needs.

* **New Feature**: Added `update` command to allow users to modify an existing application’s status.
  * **What it does**: This command lets users update the status of any stored internship application (e.g., from `INTERVIEWED` to `OFFERED`), keeping application progress accurately tracked.
  * **Justification**: Tracking the progression of applications is vital for users, as it gives them a structured overview of where they stand with each company.
  * **Highlights**: The implementation required careful handling of index-based inputs and extensive validation to prevent errors during the update process.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=blanklogic&tabRepo=AY2425S1-CS2103T-T09-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false )

* **Project management**:
  * Set up the organisation, repository, and initial processes for tP.
  * Hosted weekly meetings via Discord to discuss weekly sprint planning for milestone requirements.
  * Provided team with breakdown and summary of the week's content and meeting objectives.
  * Managed releases `v1.3`, `v1.4`, `v1.5`, and `v1.5.1`, preparing release notes and ensuring milestones were met.

* **Enhancements to existing features**:
  * Improved the `add` command by reducing the amount of mandatory parameters, allowing users to quickly add a new company name and email (Pull request [\#58](https://github.com/AY2425S1-CS2103T-T09-1/tp/pull/58)).
  * Improved the `find` command by making the keyword search more flexible, allowing users to search across company names, tags, and application descriptions (Pull request [\#179](https://github.com/AY2425S1-CS2103T-T09-1/tp/pull/179)).
  * Refined exception handling across several commands to improve error message clarity and ensure consistent application behaviour.

* **Documentation**:
  * **User Guide**:
    * Documented the `apply` and `update` commands, along with examples for each.
    * Co-Documented the `withdraw` command, fixing typos and enhancing its phrasing.
    * Enhanced existing documentation on command format and parameters to improve user understanding.
  * **Developer Guide**:
    * Added acknowledgements of AddressBook Level 3 for InternBuddy.
    * Enhanced existing instructions for manual testing, specifically the launch and shutdown.
    * Contributed to the `apply` and `update` command implementation section, detailing the command’s design and providing sequence diagrams.
    * Added the sequence diagram for `apply` and `update` and updated class diagrams to reflect structural changes made for the feature.
    * Fixed hyperlinks and formatting issues.
    * Fixed incorrect lifeline notations for all Sequence Diagrams.

* **Community**:
  * PRs reviewed (with detailed feedback): [\#31](https://github.com/AY2425S1-CS2103T-T09-1/tp/pull/31), [\#189](https://github.com/AY2425S1-CS2103T-T09-1/tp/pull/189), [\#300](https://github.com/AY2425S1-CS2103T-T09-1/tp/pull/300).
  * Reported and documented bugs in other teams' products, along with suggested fixes ([1](https://github.com/AY2425S1-CS2103T-W10-2/tp/issues/260), [2](https://github.com/AY2425S1-CS2103T-W10-2/tp/issues/313), [3](https://github.com/AY2425S1-CS2103T-W10-2/tp/issues/289)).

* **Tools**:
  * Set up automated CI/CD workflows with GitHub Actions to streamline testing and integration.
