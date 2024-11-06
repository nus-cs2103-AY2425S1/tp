---
layout: page
title: Kay Hian's Project Portfolio Page
---

### Project: ResearchRoster

ResearchRoster is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added `assign` command to (randomly) assign persons to study group(s) [\#125]()
  * What it does: allows the user to randomly assign a list of persons into study groups.
  * Justification: As our product is targeted at researchers, we anticipate the need to quickly and fairly assign participants to study groups. This feature allows the user to assign an entire list of participants in one command call.
  * Highlight: a bonus feature of this command is the ability to assign all persons in the displayed list to a single study group by inputting only one study group name.

* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=f08-2)

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Added exit message to `exit` [\#52]()
  * Expanded the `find` command to be more flexible [\#81]()
    * What's different: allows the user to find persons using different criterias besides just the name field.
    * Justification: This feature improves the product significantly because a user is able to make more accurate searches to filter the list of persons.
  * Added warning message to `edit` to warn the user of adding existing tag(s) and/or removing nonexistent tag(s) [\#159]()

* **Documentation**:
  * User Guide:
    * Updated documentation for the features `find`, `assign` and `exit`  [\#148]()
  * Developer Guide:
    * Added use cases for requirements section. [\#36]()
    * Added implementation details of the `find` feature. [\#110]()

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#39](), [\#65](), [\#102](), [\#139]()
  * Contributed to forum discussions (examples: [1](https://github.com/nus-cs2103-AY2425S1/forum/issues/216))
