---
layout: default.md
title: "Jeremy's Project Portfolio Page"
---


### Project: UniVerse

UniVerse is a **desktop app for managing contacts**, optimized for use via a **Command Line Interface (CLI)**
while incorporating a **Graphical User Interface (GUI)** for ease of use. UniVerse is designed to help you manage
detailed contact information, including academic and professional details, quickly and efficiently.

Given below are my contributions to the project.

* **New Feature**: Added the ability to find contacts by major: `findm` (Pull request [\#49](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/49))
    * What it does: Allows users to search for contacts based on specific major. This feature enables users to connect with others who are in the same field of study.
    * Justification: This feature enhances the usability of the app by enabling users to find and connect with contacts on a academic level. 
    * Highlights: The feature supports case-insensitive searches and allows users to find contacts accurately as long as the major matches the order of words in the input. Invalid characters in the search keyword trigger an error message, ensuring clean and accurate searches.

* **New Feature**: Added the ability to find contacts by work experience: `findw` (Pull request [\#69](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/69))
    * What it does: Allows users to search for contacts based on specific work experience. This feature enables users to connect with others who are have worked at a particular company.
    * Justification: This feature enhances the usability of the app by enabling users to find and connect with contacts that has or had worked at a specific company. This allows them to build more connections that can be helpful in job search.
    * Highlights: The feature supports case-insensitive searches and allows users to find contacts accurately as long as the work experience matches the order of words in the input. 
    
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=T17-1&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=jeremychiaaaaa&tabRepo=AY2425S1-CS2103T-T17-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Testing** (Pull requests [\#62](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/62), [\#76](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/76))
    * What it does: Increased test coverage for specific components, including `MaContainsKeywordsPredicate`, `FindByMajorCommand`, `FindByMajorCommandParser`, `FindByInterestCommandParser`, `FindByWorkExperienceCommandParser`, `FindByWorkExperienceCommand`, `WorkExperienceContainsKeywordsPredicate`.
    * Justification: Improved test coverage ensures reliability and correctness of the new and existing features, particularly in handling user inputs and commands.
    * Highlights: Raised the coverage from 0% to 100% for these components, contributing to the overall robustness of the application.

* **Bug Reports**:
    * DocumentationBug: Unclear error message for Add command (Issue [\#151](https://github.com/AY2425S1-CS2103T-T17-1/tp/issues/151)).
    * FeatureFlaw: Logic error in findi command which gives unintuitive outputs (Issue [\#159](https://github.com/AY2425S1-CS2103T-T17-1/tp/issues/159)).

* **Documentation**:
    * User Guide:
        * Added documentation for the `findm` and `findw` feature (Pull request [\#150](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/150)).
    
* **Project management**:
    * Scheduling and Coordination: Organized and led weekly meetings to foster team alignment, captured comprehensive meeting notes, and assigned actionable tasks, ensuring everyone was on the same page.
    * Progress Tracking: Diligently monitored task progress, maintained timelines, and adjusted priorities as needed to keep the project on course.
    * Deliverable Oversight: Proactively managed project deliverables, consistently meeting deadlines and achieving key milestones on a weekly basis, ensuring timely completion and high-quality outcomes.
