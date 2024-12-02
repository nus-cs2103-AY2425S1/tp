---
  layout: default.md
  title: "Tianyi's Project Portfolio Page"
---

### Project: AddressBook Level 3

AddressBook - Level 3 is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the ability to undo/redo previous commands, together with [Eline](elinengu.md).
  * What it does: allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
  * Justification: This feature improves the product significantly because a user can make mistakes in commands and the app should provide a convenient way to rectify them. This is especially the case when the user accidentally `clear`s the entire address book.
  * Highlights: This enhancement affects existing commands and commands to be added in the future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it interacts with many components of the app.
  * Credits: [Eline](elinengu.md) and I implemented the feature based on the *Proposed Features* section of AB3. I also wrote the tests for the feature.

* **New Feature**: Added the ability to delete module roles using the `edit` command.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=lithops-zty&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=lithops-zty&tabRepo=AY2425S1-CS2103T-F12-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project management**:
  * Managed releases `v1.3`, `v1.5`, `v1.5.1`, `v1.6` on GitHub.
  * Refined the color scheme of GitHub issue labels.

* **Enhancements to existing features**:
  * Added the ability to further filter the previous find results using `find chained`
  * Allowed the `find` command to perform a partial match on names.
  * Made the error messages of `find` and `delete` more specific.
  * Included the exact fields edited in the `edit` command's success message.

* **Documentation**:
  * User Guide:
    * Added documentation for the feature delete module role.
    * Updated the documentation for the `find` command.
    * Updated the repetition syntax.
  * Developer Guide:
    * Added the sequence diagrams for the `find` command.
    * Updated the diagrams for undo/redo.
    * Updated the `Model`'s class diagram to include the `VersionedAddressBook` class.

* **Community**:
  * 40 PRs reviewed
  * Contributed to forum discussions (examples: [1](https://github.com/nus-cs2103-AY2425S1/forum/issues/2), [2](https://github.com/nus-cs2103-AY2425S1/forum/issues/165), [3](https://github.com/nus-cs2103-AY2425S1/forum/issues/495))

