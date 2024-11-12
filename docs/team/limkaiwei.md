---
  layout: default.md
  title: "Lim Kai Wei's Project Portfolio Page"
---

### Project: SeeRee 2.0

SeeRee 2.0 is a combination of desktop address book and a scheduler for meetings application. The user interacts with
it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: `meeting-contacts`
  * What it does: allows user to filter and view contacts related to selected meetng.

* **New Feature**: backup corrupted or broken save file
  * What it does: allows automatic back up of corrupted or broken json save files for user to manually restore.
  * Justification: previously any corrupted entries will cause entire save file to be overwritten by an empty save. This will provide a way for users to retrieve any broken save files.

* **Enhancements to existing features**:
  * Enhanced `find` command searching by name and tag.

* **Documentation**:
  * User Guide:
    * Added documentation for the features `meeting-contacts` and `find`.
  * Developer Guide:
    * Added implementation details of the `meeting-contacts` feature.

* **Project management**:
  * Managed releases `v1.0.0 - v1.2.0` (3 releases) on GitHub

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=limkaiwei)

<iframe src="https://nus-cs2103-ay2425s1.github.io/tp-dashboard/#/widget/?search=limkai&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=zoom&zA=LimKaiWei&zR=AY2425S1-CS2103-F13-3%2Ftp%5Bmaster%5D&zACS=183.14285714285714&zS=2024-09-20&zFS=limkai&zU=2024-11-07&zMG=false&zFTF=commit&zFGS=groupByRepos&zFR=false&chartGroupIndex=0&chartIndex=0" frameBorder="0" width="800px" height="142px"></iframe>
