---
  layout: default.md
  title: "Jeriel's Project Portfolio Page"
---

### Project: Prudy

Prudy is an application to help Prudential financial agents manage their clients. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java.

Given below are my contributions to the project.

* **New Feature**: Enhanced Find Client Command
  * **What it does**: Allows users to find clients with more precise criteria and improved error handling for phone number formats.
  * **Justification**: This feature improves user experience by helping financial agents quickly locate client information without encountering format errors.
  * **Highlights**: Implemented defensive coding to handle invalid inputs and edge cases, making the command more robust and reliable.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s1.github.io/tp-dashboard/?search=Nimastic&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByAuthors&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20)
  * Updated and refined `FindClientCommand.java` and `FindClientCommandTest.java` for improved functionality and testing coverage.
  * Added defensive coding to `FindClientCommand.java` to handle edge cases and improve error messages.
  * Updated `build.gradle` for improved build management.
  * Configured CodeCov for test coverage tracking and applied multiple Checkstyle updates to enforce code quality.

* **Project management**:
  * Managed the merging of several pull requests to maintain an updated codebase:
    - Authored and merged pull requests such as `branch-update-faq` and contributed to `portfolio` updates for team members.

* **Enhancements to existing features**:
  * Updated the `Find Client` feature to add better error messaging for incorrect phone number formats (e.g., `Find-Client Phone Error Message`).
  * Improved the `FindCommand.java` and `FindCommandTest.java` classes with additional defensive coding to handle potential user input errors.
  * Enhanced code readability and maintainability by updating Checkstyle configurations and applying consistent formatting across files.

* **Documentation**:
  * **User Guide**:
    * Updated the FAQ section to address common user queries and improve troubleshooting for the `Find Client` command (Pull request [\#1](https://github.com/Nimastic/branch-update-faq)).
    * Added details to `UserGuide.md` to clarify usage and edge cases for new commands.
  * **Developer Guide**:
    * Added acknowledgments in `DeveloperGuide.md` for various contributions and improvements made to the project.
    * Updated `AboutUs.md` with personal details and team contributions, including roles and areas of focus.
    * Added GitHub build status badges in `README.md` to provide visibility into the project’s build health.

* **Tools**:
  * Configured and set up CodeCov to monitor test coverage across the project.
  * Managed Checkstyle configurations for consistent code quality and readability.
  * Updated GitHub badges and `README.md` to reflect the project’s build and code coverage status.
