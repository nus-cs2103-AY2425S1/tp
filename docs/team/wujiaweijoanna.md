---
layout: page
title: Jia Wei's Project Portfolio Page
---

### Project: ResearchRoster

ResearchRoster is a desktop application designed for researchers managing large groups of study participants. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 13 kLoC.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=f08-2)

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Revised the `add` command fields to meet user needs (Pull requests [\#50](), [\#65]())
    * What's different: Allows adding relevant participant data such as gender, age, and additional details while removing unnecessary fields like phone number and address. Detail field was made optional. 
    * Justification: This modification streamlines data input, ensuring that users enter only relevant information specific to research participants.
  
  * Implemented `bulk deletion` functionality (Pull request [\#77]())
    * What's different: Users can delete participants by specifying a range of indices, in addition to single-index deletion.
    * Justification: Bulk deletion saves users time when managing larger datasets, by allowing multiple deletions in a single action.
  
  * Added an `age-range search` feature (Pull request [\#123]())
    * What's different: Users can find participants within a specified age range, not just by specific age.
    * Justification: This feature is especially useful for studies requiring age-based grouping. It saves users time compared to performing multiple individual searches.

* **Documentation**:
  * User Guide:
    * Modified existing documentation of features `add`, `delete` and `find`: [\#123](), [\#161]()
  * Developer Guide:
    * Added instructions for manual testing: [\#210]()

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#79](), [\#148](), [\#320]()
