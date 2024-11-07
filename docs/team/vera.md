---
layout: default.md
title: "Vera's Project Portfolio Page"
---

### Project: UniVerse

UniVerse is a **desktop app for managing contacts**, optimized for use via a **Command Line Interface (CLI)**
while incorporating a **Graphical User Interface (GUI)** for ease of use. UniVerse is designed to help you manage
detailed contact information, including academic and professional details, quickly and efficiently.

Given below are my contributions to the project.

* **New Feature**: Added the `findu` command to search for contacts by university (Pull request [\#48](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/48))
    * What it does: Allows users to find and filter contacts based on the university they are associated with.
    * Justification: This feature is essential for students who wish to connect with peers or alumni from specific universities, enhancing networking opportunities.
    * Highlights: Implemented case-insensitive searches for accurate results and handled scenarios with invalid or missing keywords to prevent errors.

* **New Feature**: Added the `addi` command to add interests to a contact (Pull request [\#91](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/91))
    * What it does: Enables users to include multiple interests for each contact, supporting more personalized data management.
    * Justification: This feature broadens the functionality by allowing users to document common hobbies or areas of interest, making it easier to find contacts with shared activities.
    * Highlights: Updated the `Interest` class to store and manage multiple interests effectively. Ensured validation to handle input errors gracefully.

* **Enhancements to existing features**:
    * Enhanced the `Interest` class to support storing multiple interests per contact, increasing the app's usability (Pull request [\#85](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/85)).
    * Refined the UI to display interests in a user-friendly format, improving the overall interface and user experience.

* **Bug Fixes**:
    * Resolved critical alpha bugs by changing the duplicate contact check from a duplicate name to a duplicate phone number for more accurate validation.
    * Fixed issues that improved the robustness and reliability of commands and features, including user input parsing.
    * Fixed the non-functioning project website to restore usability and ensure accurate display of documentation (Pull request [\#146](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/146)).
    * Updated and modified the `style.css` file to enhance the styling and readability of the project's web pages, resulting in a polished and professional appearance (Pull request [\#154](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/154)).
    * Contributed additional tests to ensure the stability and reliability of features and bug fixes (Pull request [\#168](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/168)).

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=verakohh&tabRepo=AY2425S1-CS2103T-T17-1/tp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Testing** (Pull requests [\#51](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/51), [\#52](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/52), [\#64](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/64), [\#96](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/96), [\#98](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/98))
  * What it does: Increased test coverage for specific components, including `UniversityContainsKeywordsPredicate`, `FindByUniversityCommand`, `FindByUniversityCommandParser`, `FindByInterestCommandParser`, `AddInterestCommand`, `AddInterestCommandParser`, `Person`.
  * Justification: Improved test coverage ensures reliability and correctness of the new and existing features, particularly in handling user inputs and commands.
  * Highlights: Raised the coverage from 0% to 100% for these components, contributing to the overall robustness of the application.

* **Documentation**:
    * User Guide:
        * Formatted and updated documentation for better readability and consistency.
        * Documented new features `findu` and `addi` with detailed usage instructions (Pull request [\#130](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/130)).
    * Developer Guide:
        * Created and included UML diagrams: `AB3Commands.puml` and `UniVerseNewCommands.puml` to illustrate command structures and feature interactions.
        * Improved formatting and organization for a more comprehensive DG (Pull request [\#164](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/164)).

* **Project management**:
    * Leadership: Coordinated team meetings, tracked deliverables, and delegated tasks to ensure project milestones were met.
    * Quality Assurance: Reviewed pull requests and provided constructive feedback to maintain high code quality and consistency.
    * Oversight: Managed the progress of different features and their integration into the main project, ensuring timely submission and alignment with project goals.

---
