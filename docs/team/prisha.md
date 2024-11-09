---
layout: page
title: Prisha's Project Portfolio Page
---

### Project: ResearchRoster

ResearchRoster is a desktop application designed for researchers managing large groups of study participants. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

Given below are my contributions to the project.

* **New Feature**: Added the `export` command to allow users to export emails of listed participants.(Pull requests [\#106](), [\#190](), [\#192]())
  * What it does: Allows the user to export the emails of listed participants to a .txt file with a name specified by the user.
  * Justification: This feature improves the product significantly as the exported emails can help researchers to more conveniently contact a group of participants.
  * Highlights: This feature supports exporting of filtered or unfiltered lists of participants. This command helped me learn a lot more about the Java File class.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=f08-2)

* **Project management**:
  * Managed releases `v1.3` - `v1.5rc` (3 releases) on GitHub

* **Enhancements to existing features**:
  * Expanded `clear` command functionality to clear current listed persons from the address book (Pull request [\#119]())
    * What's different: The original version clears all participants from the address book. The update allows users to clear a filtered list of persons (in order to more selectively clear participants) or to clear an unfiltered list of participants (to clear the entire address book).
    * Justification: This update improves the product significantly because a user is able to be more selective when clearing participants. For example, it makes it more efficient for the user to clear participants belonging to a particular study group once a study is over.
    * Highlights: This feature was slightly challenging to implement as the logic of the original solution for clearing (to set the model's AddressBook to a new AddressBook) could not be applied when clearing a filtered list, so I needed to figure out a new way of clearing for this case.
  * Added `confirm` command to confirm clearing (Pull requests [\#126](), [\#222]())
    * What's different: `clear` prompts the user to type `confirm` to confirm that they want to clear or to type anything else to cancel clearing. Clearing is only done if confirmation is given.
    * Justification: This update ensures that the user is sure that they want the listed participants to be permanently removed from the address book before clearing them.
  * Updated save and load to warn user of inability to save or load data due various errors. The warning is displayed for any command or change the user makes, as long as the save/load error persists. (Pull requests [\#66](), [\#68]())
  * Updated `list` command to provide a message that there are no saved participants if the list is empty. (Pull request [\#51]())
  * Standardised messages for various commands. (Pull requests [\#112](), [\#183](), [\#194]())

* **Documentation**:
  * Wrote the README
  * User Guide:
    * Added documentation for the `export` feature (Pull request [\#196]())
    * Made major changes to the documentation for the `clear` feature (Pull request [\#196]())
  * Developer Guide:
    * Wrote the Appendix: Effort section. (Pull request [\#198]())
    * Added Use Cases `UC16 - Clear listed persons` and `UC17 - Exit`. (Pull request [\#220]())
    * Improved manual testing instructions for `clear` command. (Pull request [\#220]())
    * Added acknowledgements. (Pull request [\#220]())
    * Minor changes to UML diagram of ModelClassDiagram (Pull request [\#90]())

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#125](), [\#107](), [\#212](), [\#200]()
