---
layout: page
title: Suhayl's Project Portfolio Page
---

### Project: CareLink

CareLink is a desktop address book application targeted towards independent Geriatricians managing elderly patients with chronic conditions, someone who can type fast, prefers CLI over GUI, and often needs to manage several patients.

Given below are my contributions to the project.

* **New Feature**: Added the ability to find a person based on the given name, NRIC, email, phone number, role, or tags [\#71](https://github.com/AY2425S1-CS2103T-T13-4/tp/issues/71)
  * What it does: allows the user to find 1 or more person by using their respective details.
  * Justification: This feature enables the doctor to quickly retrieve the records of a patient using any of the given details.
  * Highlights: This is an enhancement to the already existing find feature which finds person based on their names.
    This addition features speeds up retrieval by using a person's other details.

* **New Feature**: Added the `findapp` command to search for appointments based on appointment start date, start time, end date, and end time. [\#150](https://github.com/AY2425S1-CS2103T-T13-4/tp/issues/150)
  * What it does: Allows the user to find one or more appointments by specifying start date, start time, end date, and end time and displays appointments falling withing this region.
  * Justification: This feature enables doctors to easily locate appointments without having to scroll through all entries, thereby improving efficiency in managing patient schedules.
  * Highlights: This command expands the functionality of appointment management by offering a flexible and detailed search mechanism. It supports case-insensitive searches and allows filtering based on multiple criteria, enhancing the doctor's workflow.


* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=T13&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=suhayl13&tabRepo=AY2425S1-CS2103T-T13-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
    * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
    * Updated Person class in model to include NRIC field. [\#45](https://github.com/AY2425S1-CS2103T-T13-4/tp/issues/45)

* **Documentation**:
    * User Guide:
        * Added documentation for the features `delete` and `find` [\#72]()
        * Did cosmetic tweaks to existing documentation of features `clear confirm`: [\#74]()
    * Developer Guide:
        * Added sequence diagram of the `clear confirm` feature.

* **Community**:
    * PRs reviewed (with non-trivial review comments): [\#12](), [\#32](), [\#19](), [\#42]()
    * Contributed to forum discussions (examples: [1](), [2](), [3](), [4]())
    * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
    * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

* _{you can add/remove categories in the list above}_
