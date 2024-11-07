---
layout: default.md
title: "Tze Cheng's Project Portfolio Page"
---


### Project: UniVerse

UniVerse is a **desktop app for managing contacts**, optimized for use via a **Command Line Interface (CLI)**
while incorporating a **Graphical User Interface (GUI)** for ease of use. UniVerse is designed to help you manage
detailed contact information, including academic and professional details, quickly and efficiently.

Given below are my contributions to the project.

* **New Feature**: Added the ability to add work experience: `addw` (Pull request [\#73](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/73))
    * What it does: Allows users to add work experience to existing contacts based on their index.
    * Justification: This feature enhances the usability of the app by enabling users to easily add work experience to their contacts using their index. 
    * Highlights: The feature supports various company names in the work experience input, including those with special symbols as well. Index input needs to be within current list size as well. 

* **Enhancements to existing features**:
  * Added the `WorkExp` class to a `Person` class to support future commands, increasing the app's usability (Pull request [\#47](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/47)).
    
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=T17-1&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=kuiktzecheng&tabRepo=AY2425S1-CS2103T-T17-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Testing** (Pull requests [\#84](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/84), [\#89](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/89))
    * What it does: Increased test coverage for specific components, including `AddWorkExperienceCommand`, `AddWorkExperienceCommandParser`, `AddCommandParserTest`
    * Justification: Improved test coverage ensures reliability and correctness of the new and existing features, particularly in handling user inputs and commands.
    * Highlights: Raised the coverage from 0% to 100% for these components, contributing to the overall robustness of the application.

* **Bug Reports**:
    * DocumentationBug: Unclear display message for addw command (Issue [\#156](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/156)).
    * FeatureFlaw: Accepting other special symbols in a Company's name in the Work Experience field (Issue [\#166](https://github.com/AY2425S1-CS2103T-T17-1/tp/issues/166)).

* **Documentation**:
    * User Guide:
        * Added documentation for the `addw` feature (Pull request [\#141](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/141)).
    * Developer Guide:
        * Added test input and description of commands in appendix (Instructions for manual testing appendix) (Pull request [\#176](https://github.com/AY2425S1-CS2103T-T17-1/tp/pull/176)).

* **Project management**:
    * Scheduling and Coordination: Facilitated weekly team meetings to ensure alignment, documented detailed meeting notes, and assigned clear action items, promoting cohesion and accountability. Cleared any doubts within the team. 
    * Progress Tracking: Closely tracked task progress, maintained project timelines, and re-prioritised as needed to keep everything on schedule.
    * Deliverable Oversight: Actively oversaw project deliverables, consistently meeting deadlines and hitting key milestones each week to achieve timely and high-quality results.