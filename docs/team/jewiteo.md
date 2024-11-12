---
  layout: default.md
  title: "Jewi Teo's Project Portfolio Page"
---

### Project: SeeRee 2.0

SeeRee 2.0 is a combination of desktop address book and a scheduler for meetings application. The user interacts with
it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **Enhancement**: Add GUI components to display the schedule.
  * What it does: Displays meetings and the associated details sorted by days. Commands can be used to search for specific periods to be displayed by the GUI.
  * Justification: These components allow for users to better visualise their meetings and give context for the schedule related commands.
  * Highlights: The implementation was challenging to achieve proper OOP and abstraction as the data was reliant on a single list, 
yet had to display different values for the different days. 

* **New Feature**: Implement `delete-schedule`
  * What it does: Allowed users to delete a specified meeting.

* **Project management**:
  * Managed releases `v1.0.0 - v1.2.0` (3 releases) on GitHub.

* **Documentation**:
  * User Guide:
    * Added documentation for the features `delete-schedule` and `favourite`.
    * Updated example images for commands.
    * Updated command summary.
    
  * Developer Guide:
    * Added implementation details of the `delete-schedule` feature.
    * Added manual testing cases for `delete-schedule`.
    * Updated UI UML Diagram.
    * Added `delete-schedule` sequence diagram.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=jewiteo&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20)
