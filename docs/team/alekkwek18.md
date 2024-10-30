---
layout: page
title: Alek Kwek's Project Portfolio Page
---

### Project: TalentConnect

TalentConnect is a desktop address book application optimized for solo freelancer recruiters. It is a brownfield project adapted from [AddressBook Level 3](https://se-education.org/addressbook-level3/). The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added `ScreenCommand`.
  * **What it does**: Allows the user to filter and display contacts that match specific job requirements.
  * **Justification**: This command enhances the recruiter’s efficiency by enabling faster matching of candidates to job listings.
  * **Highlights**: Implementing this command involved defining filtering criteria that integrate seamlessly with existing job and contact entities.

* **New Feature**: Added `UnmatchCommand`.
  * **What it does**: Allows the user to remove associations between jobs and contacts.
  * **Justification**: This feature improves data accuracy by allowing recruiters to update job matches as candidate-job statuses change.
  * **Highlights**: The implementation required careful handling of relationship data, as it impacted associated entities and existing commands.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=alekkwek18&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=AlekKwek18&tabRepo=AY2425S1-CS2103-F13-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * NIL

* **Enhancements to existing features**:
  * Refactored `AddCommand` to `AddContactCommand` to improve specificity and clarity.
  * Refactored the original functionality of `DeleteCommand` into `DeleteContactCommand` to allow the user to delete a specific contact from the address book.
  * Refactored `DeleteCommand` into a generic command `DeleteCommand<T>` to support future implementations of `DeleteContactCommand`,`DeleteJobCommand` and `DeleteCompanyCommand`.
  * Refactored `Person` class from tags to skills.

* 
* **Documentation**:
  * User Guide: NIL
  * Developer Guide:
    * Updated UML diagram to reflect changes in `DeleteContactCommand` and generic refactoring of `DeleteCommand`. (pull requests [#135](https://github.com/AY2425S1-CS2103-F13-4/tp/pull/135)) 

* **Testing**:
  * Added test cases for `DeleteContactCommand`.
  * Refactored test cases for `AddContactCommand` to align with the updated command structure.
