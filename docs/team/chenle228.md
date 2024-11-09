---
  layout: default.md
  title: "Chen Le's Project Portfolio Page"
---


### Project: UniVerse

UniVerse is a **desktop app for managing contacts**, optimized for use via a **Command Line Interface (CLI)**
while incorporating a **Graphical User Interface (GUI)** for ease of use. UniVerse is designed to help you manage
detailed contact information, including academic and professional details, quickly and efficiently.

Given below are my contributions to the project.

* **New Feature**: Added the ability to find contacts by interest: `findi` (Pull request [\#93](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/93))
  * What it does: Allows users to search for contacts based on specific interests. This feature enables users to connect with others who share similar personal interests.
  * Justification: This feature enhances the usability of the app by enabling users to find and connect with contacts on a personal level. By searching by interest, users can form networks based on common hobbies or areas of passion.
  * Highlights: The feature supports case-insensitive searches and allows users to find contacts accurately as long as the interest matches the order of words in the input. Invalid characters in the search keyword trigger an error message, ensuring clean and accurate searches.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=T17-1&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20)

* **Enhancements to existing features**: Expanded `add` Command (Pull requests [\#45](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/45), [\#46](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/46), [\#59](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/59))
  * What it does: Extends the `add` command to include fields such as university, major, work experience, and interests.
  * Justification: The enhancement was necessary to create a comprehensive profile for each contact, which supports building both academic and professional networks.
  * Highlights: This enhancement allows users to include multiple interests along with university, major and work experience, providing flexibility in describing contacts.

* **Testing** (Pull requests [\#103](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/103), [\#105](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/105))
  * What it does: Increased test coverage for specific components, including `MajorContainsKeywordsPredicate`, `FindByMajorCommand`, `FindByMajorCommandParser`, and `FindByInterestCommandParser`.
  * Justification: Improved test coverage ensures reliability and correctness of the new and existing features, particularly in handling user inputs and commands.
  * Highlights: Raised the coverage from 0% to 100% for these components, contributing to the overall robustness of the application.

* **Bug Reports**:
  * DocumentationBug: Extra commas displayed in the search result message (Issue [\#133](https://github.com/AY2425S1-CS2103T-T17-1/tp/issues/133)).
  * FeatureFlaw: Restriction on the “&” symbol in `workExperience` field limits the app’s real-world applicability (Issue [\#134](https://github.com/AY2425S1-CS2103T-T17-1/tp/issues/134)).

* **Bug Fixing**: `findi` (Pull request [\#193](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/193))
  * Simplified Search Functionality: Refactored the "find by interests" feature to allow only single interest searches, enhancing usability and reducing confusion for users. 
  * Error Handling for Invalid Inputs: Implemented error messages for unsupported input formats, including:
    - Multiple interest keywords separated by spaces. 
    - Invalid command structures without proper prefixes. 
  * Updated Command and Parser Classes: Modified `FindByInterestCommand` and `FindByInterestCommandParser` to accommodate the new single interest search logic and ensure compliance with the updated input requirements.
  * Predicate Logic Adjustment: Streamlined the `InterestContainsKeywordsPredicate` to support the new searching mechanism by accepting a single keyword instead of handling multiple AND/OR conditions.
  * Test Case Revisions: Enhanced the test cases in `FindByInterestCommandParserTest` and `FindByInterestCommandTest` to validate the new behavior of the feature, ensuring correct parsing and execution based on the updated requirements.
  
* **Documentation**:
  * User Guide:
    * Added documentation for the `findi` feature (Pull request [\#132](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/132)).
  * Developer Guide:
    * Added documentation for the user profile, value proposition, and user stories (Pull request [\#31](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/31)). 
    * Added documentation for the user stories and use cases for the `findw` and `findi` features (Pull request [\#172](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/172)).
  
* **Project management**:
  * Scheduling and Tracking: Initiated weekly meetings, took detailed meeting notes, assigned tasks, and tracked progress throughout the project.
  * Deliverables and Deadlines: Ensured that project milestones and deliverables were met consistently on a weekly basis.
