---
  layout: default.md
  title: "Raghava Chittidi's Project Portfolio Page"
---

### Project: HireMe

HireMe is a **desktop application for managing internship applications, optimized for use via a Command Line Interface** (CLI)
while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, HireMe can get your internship tracking tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: Added a delete command which deletes an internship application from the list by index.
  * What it does: Deletes the internship application at the specified index.
  * Justification: This feature is necessary for the HireMe application to work properly as users would want to delete internship applications that they no longer want to track.

* **New Feature**: Added the ability sort the list of internship applications.
  * What it does: Allows the user to sort the list in ascending or descending order, by date of application.
  * Justification: This feature is a nice-to-have as it allows users to sort the list and follow up on any old internship applications where the company has not gotten back to them.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=Raghava-Chittidi&tabRepo=AY2425S1-CS2103T-W09-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Managed release `v1.4` (1 release) on GitHub

* **Enhancements to existing features**:
  * Added defensive programming through the use of assertions and exceptions.
  * Added logging to enable better debugging in the future.
  * Ensured error messages are consistent for all commands to reduce ambiguity.

* **Testing**:
  * Added extensive test cases for model and parser packages.

* **Documentation**:
  * User Guide:
    * Added documentation for the `delete` feature
    * Added documentation for the `sort` feature
  * Developer Guide:
    * Added implementation details of the `help`, `delete` and `sort` features.
    * Added sequence diagrams for `help`, `delete` and `sort` features.
    * Added user stories for `help`, `delete` and `sort` features.
    * Added use cases for `list` and `sort` features.
    * Added terms into glossary.
    * Added Non-Functional Requirements (NFRs).

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#43](https://github.com/AY2425S1-CS2103T-W09-3/tp/pull/43), [\#19](https://github.com/AY2425S1-CS2103T-W09-3/tp/pull/57), [\#105](https://github.com/AY2425S1-CS2103T-W09-3/tp/pull/105), [\#188](https://github.com/AY2425S1-CS2103T-W09-3/tp/pull/188)
