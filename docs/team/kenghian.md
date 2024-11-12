---
layout: page
title: Keng Hian's Project Portfolio Page
---

### Project: TalentConnect

TalentConnect is a desktop address book application optimized for solo freelancer recruiter. It is brownfield project adapted from [AddressBook Level 3](https://se-education.org/addressbook-level3/). The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to list Job listing.
  * What it does: Allows the user to list all job listings in the address book.
  * Justification: Recruiters' work is closely tie to job matching, thus having job listing represented in the system allows features that assist this aspect of a recruiter's workflow.
  * Highlights: This enhancement require the team to think carefully on definition of a unique job and how it would work with other entities in the model.
* **New Feature**: Added the ability match a contact to a job listing.
  * What it does: Allows the user to match a contact to a job listing for job matching purpose.
  * Justification: This feature improves the product for our target users because having this information allows the users to firstly, filter out contacts who already have a job during job matching (`screen job`); secondly, better manage contacts via the job they matched to (`view company`).
  * Highlights: This enhancement proves challenging when implementing associations in json. Having established this association also comes with consideration on the effects on existing commands. This feature is finally complete after multiple discussions among the team, and changes in design across iterations.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=KengHian&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=KengHian&tabRepo=AY2425S1-CS2103-F13-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**: 
  * Managed releases v1.3 (1 release) on GitHub

* **Enhancements to existing features**:
  * Enables two word commands for the features `list` and `add` make commands for intuitive (Pull requests [#80](https://github.com/AY2425S1-CS2103-F13-4/tp/pull/80), [#81](https://github.com/AY2425S1-CS2103-F13-4/tp/pull/81))
  * Updated the GUI to display contact, job listing and company lists together (Pull requests [#105](https://github.com/AY2425S1-CS2103-F13-4/tp/pull/105))

* **Documentation**:
  * User Guide:
    * Nil
  * Developer Guide:
    * Updated Product scope (Pull requests [#19](https://github.com/AY2425S1-CS2103-F13-4/tp/pull/19))
    * Updated Product MVP user stories (Pull requests [#31](https://github.com/AY2425S1-CS2103-F13-4/tp/pull/31))
    * Added use cases for `screen job` command (Pull requests [#43](https://github.com/AY2425S1-CS2103-F13-4/tp/pull/43))

* **Community:**:
  * PRs reviewed (with non-trivial review comments): [#205](https://github.com/nus-cs2103-AY2425S1/ip/pull/205), [#296](https://github.com/nus-cs2103-AY2425S1/ip/pull/296)

