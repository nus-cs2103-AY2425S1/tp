---
layout: default.md
title: "Kelly Wong's Project Portfolio Page"
---

### Project: HireMe

HireMe is a **desktop application for managing internship applications, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, HireMe can help you manage your internship tracking tasks faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: Architected and implemented a robust model schema, creating foundational model classes essential for application functionality.
  * Details: Developed critical model classes, including `Company`, `Date`, `Email`, `InternshipApplication`, `Name`, `Role`, and `Status`, which serve as the building blocks for the entire model structure.
  * Justification: These classes establish a scalable, clear, and maintainable framework, streamlining data flow and ensuring efficient data management across the application. This structured approach optimizes performance and paves the way for future feature expansion with minimal refactoring.

* **New Feature**: Added a status command to update the status of an internship application.
  * What it does: Allows users to update the status of an internship application to `ACCEPTED`, `PENDING`, or `REJECTED`.
  * Justification: This feature is crucial for tracking the current stage of each application, enabling users to keep organized records.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=woke02&tabRepo=AY2425S1-CS2103T-W09-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Managed releases `v1.3` and `v1.5` on GitHub, overseeing feature completeness and stability.

* **Enhancements to existing features**:
  * Added logging to improve future debugging processes.
  * Ensured error messages are consistent across models to reduce ambiguity.
  * Enhanced defensive programming by using assertions and exceptions for increased robustness.

* **Testing**:
  * Added extensive test cases for the models and certain parser commands to ensure code reliability.

* **Documentation**:
  * **User Guide**:
    * Documented the `help`, `status`, `clear`, and `exit` features.
    * Documented on how to use the user guide
  * **Developer Guide**:
    * Added implementation details for the `status` feature.
    * Created sequence diagrams for `status` features.
    * Added user stories for the `find` and `status` features.
    * Added use cases for the `find` and `status` features.
    * Created an activity diagram for the `status` feature.
