---
layout: default.md
title: "Kelly Wong's Project Portfolio Page"
---

### Project: HireMe

HireMe is a **desktop application for managing internship applications, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, HireMe can help you manage your internship tracking tasks faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: Architected and implemented a robust model schema, creating foundational model classes essential for application functionality.
  * Details: Developed critical model classes, including `Company`, `Date`, `Email`, `InternshipApplication`, `Name`, `Role`, and `Status`, which serve as the building blocks for the entire model structure.
  * Justification: These classes establish a scalable, clear, and maintainable framework, streamlining data flow and ensuring efficient data management across the application. This structured approach optimizes performance and paves the way for future feature expansion with minimal refactoring.

* **New Feature**: Added a status command to update the status of an internship application.
  * What it does: Allows users to update the status of an internship application to `ACCEPTED`, `PENDING`, or `REJECTED`.
  * Justification: This feature is crucial for tracking the current stage of each application, enabling users to keep organized records.

* **Project management**:
  * Managed releases `v1.3` and `v1.5` on GitHub, overseeing feature completeness and stability.

* **Enhancements to existing features**:
  * Added logging to improve future debugging processes.
  * Ensured error messages are consistent across models to reduce ambiguity.
  * Enhanced defensive programming by using assertions and exceptions for increased robustness.

* **Testing**:
  * Added extensive test cases for the models and certain parser commands to ensure code reliability.

* **Documentation**:
  * **User Guide**:
    * Documented the `help`, `status`, `clear`, and `exit` features.
    * Provided instructions on how to download and run the app.
  * **Developer Guide**:
    * Added implementation details for the `status` feature.
    * Created sequence diagrams for `status` features.
    * Added user stories for the `find` and `status` features.
    * Added use cases for the `find` and `status` features.
    * Created an activity diagram for the `status` feature.
