---
  layout: default.md
  title: "Kai Xuan's Project Portfolio Page"
---

### Project: SellSavvy

SellSavvy is a desktop application for tech-savvy independent sellers/dropshipping business owners to manage their customers' contacts and their orders, aiming to streamline their online drop-shipping management. It offers a centralised platform to organise customer contacts, track order deliveries and store the data.

Given below are my contributions to the project.

* **New Feature**: Added the ability to filter a customer's orders by status.
  * What it does: allows the user to filter the displayed order list by specified order statuses.
  * Justification: This feature facilitates convenience of users in order delivery management, for instance when users want to only view and operate on pending orders that have yet to be completed.
  * Highlights:
    * Predicate creation and handling for order `Status`.
    * This enhancement affects existing commands and handling of order parameters. It required an in-depth analysis of design alternatives.

* **New Feature**: Customer `Name`, Order `Item` and other parameter input handling to contextualise user order management.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=kaixuan477&tabRepo=AY2425S1-CS2103T-F14a-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Enhancements to existing features**:
  * Augmented customer classes into order and order list classes with relevant parameter handling (Pull requests [\#39](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/39).
  * Enhanced storage functionality to accommodate customer order lists and respective orders (Pull requests [\#70](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/70). 

* **Documentation**:
  * User Guide:
    * Added documentation for the features `filterorder` and storage.
    * Made cosmetic improvements to existing documentation with command warnings.
    * Supported UG instructions with relevant screenshots.
  * Developer Guide:
    * Added user stories with priorities for SellSavvy functionalities.
    * Detailed group contributions to evolving AB3 in `Appendix - Effort`.

* **Community**:
  * PRs reviewed (with non-trivial review comments), e.g. [\#82](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/82).
  * Issue management by creating and managing milestones and labels, managing improvements by issues.
  * Reported bugs and suggestions for other teams in the class.