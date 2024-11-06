---
  layout: default.md
  title: "Zwe Zeya's Project Portfolio Page"
---

### Project: HireMe

HireMe is a **desktop application for managing internship applications, optimized for use via a Command Line Interface** (CLI)
while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, HireMe can get your internship tracking tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: Added the ability add an internship application entry.
    * What it does: Allows the user to add a new internship application entry.
    * Justification: This feature is necessary for the functionality of HireMe as users need to be able to add internship applications so that they can track them.
    * Highlights: This feature addresses the compatibility issues of the old AddressBook with the functional requirements of HireMe.

* **New Feature**: Added a chart command which summarises the statuses of all internship applications
  * What it does: Displays a pie chart that gives insights to the statuses of all internship applications
  * Justification: This feature gives a visual representation of the statuses of all internship applications which makes HireMe more interactive.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=ZweZeya&tabRepo=AY2425S1-CS2103T-W09-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Enhancements to existing features**:
    * Decoupled the validation from the model by creating an abstract class `Validator`

* **Documentation**:
    * User Guide:
        * Added documentation for the `chart` feature
    * Developer Guide:
        * Added implementation details of the `add` and `chart` features.
        * Added sequence diagrams for `add`, `chart` and `exit` features.
        * Added use cases for `add`, `chart` and `exit` features.

* **Community**:
    * Reviewed PRs
