---
  layout: default.md
  title: "Kheng Yang's Project Portfolio Page"
---

### Project: SellSavvy

SellSavvy is a desktop application for tech-savvy independent sellers/dropshipping business owners to manage their customers' contacts and their orders, aiming to streamline their online drop-shipping management. It offers a centralised platform to organise customer contacts, track order deliveries and store the data.

Given below are my contributions to the project.

* **New Feature**: Added the ability to add orders under customers.
  * What it does: allows the user to add order under a specified customer.
  * Justification: This feature allows users to add orders they want to keep track of under each customer.
  * Highlights: This enhancement includes creating test cases, which can be challenging as previous test cases rely on the fact that every attribute of a `Person` is immutable, while the `OrderList` is not. Hence, new methods need to be created specifically for use in order-related test cases.

* **New Feature**: Added the ability to mark an order as completed.
  * What it does: allows user to mark an order as completed. In what way this order is completed is up to the user (e.g. the order has been delivered)
  * Justification: This feature allows users to indicate whichever orders are "Completed" without the need to delete the order from the list, hence allowing users to keep an order history of the cusatomer as well.
  * Highlights:
    * This enhancement includes creating a `Status` enumeration for each order and allowing this to be stored under each order in the storage and data file.
    * This enhancement includes creating a GUI display for the order status which allows the user to easily identify what status each order is under.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=awdse22&tabRepo=AY2425S1-CS2103T-F14a-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Managed releases `v1.3` and `v1.5.1` (2 releases) on GitHub
  * Setting up of team organization and repository.

* **Enhancements to existing features**:
  * Add messages/user feedback to GUI display for if there are no orders/customers (Pull requests [\#162](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/162))
  * Add command aliases for users to execute commands more conveniently (Pull requests [\#190](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/190))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `addorder` and `markorder` (Pull requests [\#81](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/81), [/#97](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/97))
    * Did various fixes and cosmetic tweaks to overall documentation: (Pull requests [/#211](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/211), [\#212](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/212), etc.)
  * Developer Guide:
    * Added use cases for newer features and update use cases for final product submission.
    * Added various potential/planned enhancements for future versions.

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#82](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/82), [\#98](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/98), [\#100](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/100), [\#210](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/210), [/#39](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/39), [/#140](https://github.com/AY2425S1-CS2103T-F14a-2/tp/pull/140)
  * Reported bugs and suggestions for other teams in the class

