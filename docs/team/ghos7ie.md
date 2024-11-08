### Project: T_Assistant

T_Assistant is a **desktop app for CS2103 tutors managing their students, groups and tasks** optimized for use via a
Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).

If you can type fast, T_Assistant can get your contact management tasks done faster than traditional GUI apps.
Given below are my contributions to the project.

### Lewis Lye

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=ghos7ie&breakdown=true)
* **Github**: [ghos7ie](https://github.com/ghos7ie)

<br>

* **Enhancements implemented**:
    * **New Feature [#62](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/62)** : Added Task class
        * <u>What it does</u>: Base implementation of Task class, Deadline class and status enum.
        * <u>Highlight</u>: Deadline class uses strict resolving and `uuuu` for year to deal with common invalid dates.
    * **New Feature [#74](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/74)** : Added Delete Group Command
        * <u>What it does</u>: Deletes Group from T_Assistant. Also updates Student and Tasks related to the Group
          deleted.
    * **Refactored UI [#102](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/102)**: Updates the details shown in
      Student and Group Panels
        * <u>What it does</u>: Adds group label in Student Panel and reformat list of students in each Group.
        * <u>Why do this</u>: Improves UX so that user does not have to switch between panels to see information.
    * **Refactored Feature** [#121](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/121): Improve logic when loading
      Students from `addressbook.json`
        * <u>What it does</u>: Adds a special character that denotes a student has no group.<br>Updated Student class
          structure to use `Optional<GroupName>` rather than `Optional<Group>`
        * <u>Why do this</u>: Reduces number of places to update fields when commands are run.
    * **New Feature [#143](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/143)**: Added shorthand for all commands
        * <u>Why do this</u>: Command words were getting a bit long as features were added so adding shorthand will
          allow users to be able to quickly use commands once they are familiar with them.
    * **New Feature [#168](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/168), [#169](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/169), [#171](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/171)**: Added Find Student, Group and Task commands
        * <u>What it does</u>: Allows user to search each list of objects
        * <u>Challenges</u>: Initial implementation attempted to use [FuzzyWuzzy](https://github.com/xdrop/fuzzywuzzy),
          however, due to challenges in getting consistent results, reverted back to using `String.contains()`.
    * **Refactored Feature [#197](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/197)**: Update command word matching
      to be case-insensitive
        * <u>What it does</u>: Allows user to type in command words in any case.
    * **Refactored Feature [#198](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/198), [#272](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/272)**: Update relevant comparators to be case-insensitive
        * <u>Why do this</u>: Makes more sense for the updated parameters to be case-insensitive due to their respective
          natures and use cases.
    * **New Feature [#227](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/227)**: Added Add Existing Task to Groups
        * <u>What it does</u>: Allows user to add pre-existing tasks to new groups.
        * <u>Why do this</u>: User might add groups after creation of tasks, this allows them to assign tasks to groups
          without recreating them.
    * **Refactored Class [#272](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/272)**: Updated GroupName parsing
        * <u>What it does</u>: Updates GroupName regex to accept only names in formats of group for CS2103/T.
        * <u>Why do this</u>: Since T_Assistant is built for TAs of CS2103, it makes sense that GroupName should respect
          the course's naming for groupings.
    * **Refactored Class [#272](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/272)**: Updated Deadline parsing
      error messages
      * <u>What it does</u>: Gives more information on what user inputted deadline has
      * <u>Challenges</u>: Due to inexperience with `DateTimeException`, unable to give more verbose error.
        See [#264](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/264)
    * **Refactored Feature [#291](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/291)**: Add Existing Task to Group
      Command error messages
        * <u>What it does</u>: Informs user of which part of their command went wrong.
        * <u>Why do this</u>: Since the function can take in multiple groups, it is likely the user might mistype
          certain group names or add duplicates, this allows them to see where they went wrong.
    * **Bug Fix [#308](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/308)**: Fixed Groups not appearing in `lt` after
      mark command is ran
        * <u>Bug description</u>: Groups were being filtered away due to mark command returning a filtered list.
    * **Bug Fix [#330](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/330)**: Fixed Edit Group command not updating
      `Optional<GroupName>` for Students inside the Group
    * **Bug Fix [#336](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/336)**: Fixed `setTask()` method in Group class
      to not take in index
        * <u>Bug description</u>: Index supplemented was based on global task list, which did not correspond to the
          index the task is located at within the Group.
        * <u>Fix</u>: Updated method to search for original Task before replacing it with the new one.

<br>

* **Documentation**:
    * <u>User Guide</u>:
        * [#235](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/235): Replaced UI and Help image, added existing
          commands to command summary, updated notes on features, update example commands
        * [#239](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/239): Set up template for features for the team to use
        * [#243](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/243): Updated user stories to match implementation
        * [#252](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/252): Added Find Student feature guide
    * <u>Developer Guide</u>:
        * [#26](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/26): Added use stories and use case
        * [#176](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/176): Added implementation details for Delete Group
          Command
        * [#292](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/292): Added activity diagram to Delete Group Command
        * [#298](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/298): Updated previous portions of AB3 to match
          current implementation of T_Assistant, updated links to point to team repo, updated Glossary
        * [#319](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/319): Updated NFRs for project
        * [#324](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/324): Added manual test steps

<br>

* **Contribution to team-based tasks**:
    * Kept track of deliverables
    * Set up team organisation and team repository
    * Removed mentions of AB3 in AddressBook, User Guide and Developer Guide
        * Updated help link to point to team repo
        * Updated links within UG and DG to point to their respective locations in team repo
    * Maintaining the issue tracker
    * Enabled assertions [#155](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/155)
    * Release Management for v1.4, v1.5 and v1.5.1
    * Updated structure of `JsonAdaptedGroup` and upgraded `JsonSerializableAddressBook` to support Group and Task
      storage
    * Updated structure of `JsonAdaptedStudent`
    * Update `SampleDataUtil.java` for sample data that will be used by testers
    * Suggested improvements to validations of Name [#261](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/261) and
      Email [#311](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/311)
    * Updated major portions of both UG and DG
    * Contributed to proofreading and formatting for UG and DG
    * Refer to PR section below
      * Contributed to reviewing PRs for code changes
      * Contributed to testing and bug reporting

<br>

* **Review/ mentoring contributions**:
    * Reviewed
      PRs: [#48](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/48), [#60](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/60), [#73](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/73), [#77](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/77), [#79](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/79), [#82](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/82), [#93](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/93), [#94](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/94), [#95](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/95), [#103](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/103), [#111](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/111), [#114](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/114), [#127](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/127), [#128](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/128), [#145](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/145), [#150](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/150), [#153](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/153), [#164](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/164), [#180](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/180), [#184](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/184), [#199](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/199), [#200](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/200), [#219](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/219), [#223](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/223), [#224](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/224), [#227](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/227), [#254](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/254), [#258](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/258), [#259](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/259), [#272](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/272), [#295](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/295), [#301](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/301), [#318](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/318)
    * Bug
      Reports:   [#108](https://github.com/AY2425S1-CS2103-F12-2/tp/pull/108), [#116](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/116), [#119](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/119), [#120](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/), [#162](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/162), [#175](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/175), [#179](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/179), [#185](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/185), [#188](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/188), [#189](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/189), [#190](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/190), [#191](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/191), [#192](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/192), [#193](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/193), [#196](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/196), [#225](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/225), [#240](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/240), [#242](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/242), [#248](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/248), [#261](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/261), [#266](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/266), [#267](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/267), [#268](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/268), [#270](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/270), [#273](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/273), [#280](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/280), [#328](https://github.com/AY2425S1-CS2103-F12-2/tp/issues/328)

<br>

* **Contributions beyond the project team**:
    * Clarification if CI failure will result in
      penalisation ([#531](https://github.com/nus-cs2103-AY2425S1/forum/issues/531))
    * Requesting use of Library: FuzzyWuzzy ([#543](https://github.com/nus-cs2103-AY2425S1/forum/issues/543))
    * Clarification on UML diagram deadline in Week 10 ([#574](https://github.com/nus-cs2103-AY2425S1/forum/issues/574))