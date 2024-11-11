---
layout: page
title: Pon Harish's Project Portfolio Page
---

# Project: CareLink

CareLink is a desktop address book application targeted towards independent Geriatricians managing elderly patients with chronic conditions, someone who can type fast, prefers CLI over GUI, and often needs to manage several patients.

Given below are my contributions to the project.

## V1.3

* **New Feature**: Added the ability to find a patient or caregiver based on the given NRIC [\#71](https://github.com/AY2425S1-CS2103T-T13-4/tp/issues/71)
  * What it does: allows the user to find 1 or more person by using their NRIC.
  * Justification: This feature enables the doctor to quickly retrieve the records of a patient using their NRIC.
  * Highlights: This is an enhancement to the already existing find feature which finds person based on their names. 
  This addition features speeds up retrieval by using a person's unique NRIC.
  * Worked on this feature together with [Suhayl](suhayl13.md)


* **Enhancements to existing features**:
  * Updated the Parser to incorporate a new NRIC field [\#62](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/62)


* **Documentation**:
  * User Guide:
    * Added documentation for the FindNric feature


## V1.4
* **New Feature**: Added new User Interface for Find Command [\#106](https://github.com/AY2425S1-CS2103T-T13-4/tp/issues/106)
  * What it does: Provides a comprehensive list of details for the person being found.
  * Justification: This feature enables the doctor to view all details of a patient or caregiver.
  * Highlights: This is an enhancement to the existing list which provides brief details on all persons.


* **Enhancements to existing features**:
    * Updated the Constructor of CommandResultClass to prepare for the new UI [\#127](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/127)
    * Updated the existing UI to display new fields such as NRIC and roles [\#137](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/137)
  

* **Community**:
  * PRs reviewed (with non-trivial review comments): [\#130](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/130)
  * Updated build.gradle to enable assertions [\#154](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/154)


* **Documentation**:
  * UML Diagrams:
    * Updated UML diagram to reflect new features [\#138](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/138)


## V1.5
* **New Feature**: Added new User Interface for Find Appointment Command [\#106](https://github.com/AY2425S1-CS2103T-T13-4/tp/issues/106)
  * What it does: Provides a list of details such as patient name, NRIC, phone, email, tags, appointment date and time for the matching appointments.
  * Justification: This feature enables the doctor to view the appointments within the given time frame.


* **New Feature**: Created Find Appointment Command [\#166](https://github.com/AY2425S1-CS2103T-T13-4/tp/issues/166)
  * What it does: Used for creating a findappointment command object upon parsing `findapp` command.
  * Justification: This feature enables the doctor to view the appointments within the given time frame.
  * Worked on this feature together with [Suhayl](suhayl13.md)


* **Enhancements to existing features**:
  * Updated the Constructor of CommandResultClass to prepare for the new UI [\#164](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/164)
  * Updated the UI to display the product name and icon [\#176](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/176)


* **Documentation**:
  * UML Diagrams:
    * Updated UML diagram to reflect the changes to UI [\#177](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/177)
  * Developer Guide:
    * Added details about the changes to UI [\#177](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/177)
  * User Guide:
    * Added documentation for the features `find` and `findapp` [\#173](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/173)
    * Updated all the screenshots to reflect the current UI [\#219](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/219)


* **Bug Fixes**:
  * Update `ModelManager` [\#208](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/208)
    * Updated the `ModelManager` to handle the updating of the model when `addlink` or `deletelink` commands were executed.
  * Update `EditCommand` [\#209](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/209)
    * Updated the prompt displayed to the user in `EditCommand`.
  * Update `ClearCommand` [\#210](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/210)
    * Updated the confirmation prompt in `ClearCommand`.
  * Update `AddCommand` [\#211](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/211)
    * Updated the message displayed upon successful execution of the `AddCommand` to include the person's NRIC.
    * Updated the prompt displayed to user in `Addcommand` to make roles field compulsory.
  * Update `DeleteAppointmentCommand` [\#212](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/212)
    * Updated the prompt displayed to the user in `DeleteAppointmentCommand`.
  * Update `EditAppointmentCommand` [\#213](https://github.com/AY2425S1-CS2103T-T13-4/tp/pull/213)
    * Updated `EditAppointmentCommand` to handle the case when patient is not found.

## V1.6
* **Documentation**:
  * Developer Guide:
    * Fixed errors in the Developer Guide.
