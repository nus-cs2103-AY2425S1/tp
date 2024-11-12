[![CI Status](https://github.com/se-edu/addressbook-level3/workflows/Java%20CI/badge.svg)](https://github.com/AY2425S1-CS2103T-T09-2/tp/actions)

![Ui](docs/images/successimage.png)

# TALENT SG

### Team Project:
Aditya, Darren, Dominic, Stanley & Rithani

## Table of Contents:
## 📑 Table of Contents:
- [🎯 Target User](#target-user)
- [💡 Value Proposition](#value-proposition)
- [🚀 Project Direction](#project-direction)
- [📝 User Stories](#user-stories)
- [🔍 Use Cases](#use-cases)
- [⚙️ Features](#features)
- [🧠 Non-functional Requirements](#non-functional-requirements)
- [📖 Glossary](#glossary)

---
## Target User:

Our primary target users are **Recruiters** and **HR professionals** who are responsible for managing job candidates and employee information. These users typically work in fast-paced environments where efficient management of candidate and employee data is crucial for streamlining the recruitment process and ensuring organizational growth.

---

## Value Proposition:

TalentSG aims to simplify and enhance the recruitment process for HR professionals and recruiters. The application will provide robust features for tracking applicants, managing interview schedules, and maintaining comprehensive records of employment details. By centralizing these tasks in one intuitive platform, TalentSG will help users reduce administrative burden, improve efficiency, and make better hiring decisions.

---

## Project Direction:

TalentSG will evolve from the existing AddressBook-Level3 (AB3) codebase, transforming it into a powerful contact management tool for recruitment and HR management. Key areas of development include:

- **Customizable Applicant Profiles**: Recruiters can add, modify, and manage detailed profiles for job candidates.
- **Employment Record Management**: Tools for managing employee information post-hiring.
- **Search and Filter Enhancements**: Advanced search and filtering options to locate candidate profiles.
- **Data Import/Export**: Import data from other systems and export records for reporting.
- **Improved User Experience**: Tweak the UI for HR professionals, with enhanced data visualization and intuitive navigation.

---

## Brainstormed User Stories for TalentSG:

| ID  | As a/an            | I want to be able to...                                        | So that I can...                                    |
| --- | ------------------ | -------------------------------------------------------------- | --------------------------------------------------- |
| 1   | recruiter           | add new candidate profiles                                     | keep track of all candidates applying for positions |
| 2   | recruiter           | edit candidate profiles                                        | update candidate information as new details arise   |
| 3   | HR professional     | view a list of all candidates                                  | easily access any candidate’s details on demand     |
| ... | ...                 | ...                                                            | ...                                                 |

*Above is only a fraction of the ideas we have brainstormed.*

---

## Selecting User Stories for the MVP:

The selected user stories for the MVP (Minimium Viable Product) include:
- **📝Add/Edit/Delete Candidate Profiles**
- **👥 View a List of Candidates**
- **🔍Search Candidates by Criteria**
- **📊Track Candidate Status**
- **📈View Statistics for Candidates**

These stories form the core of the MVP, providing basic functionality for recruiters and HR professionals.

---


## Feature Specification for TalentSG:

| Feature                         | Purpose                                                      |
| -------------------------------- |--------------------------------------------------------------|
| **Add/Edit/Delete Candidate**    | Allows the recruiter to manage candidate profiles            |
| **View Candidate List**          | Displays a list of all candidates                            |
| **Search Candidates by Criteria**| Enables recruiters to search candidates by skills or roles   |
| **Track Candidate Status**       | Track application status like Interviewed, Onboarding, Hired |
| **View Statistics for Candidates**| View a summary of application status for all the candidates  |

---

## Use Cases

### Use Case 1: Add a New Candidate Profile
**Actor**: Recruiter

**Preconditions**: The system is running, and the recruiter is logged into the application.

**Main Success Scenario**:
1. Recruiter selects the "Add Candidate" option.
2. System prompts recruiter to input candidate details (name, phone, email, address, role).
3. Recruiter enters candidate details.
4. System confirms that the details are valid and adds the candidate profile.
5. System displays a success message: "Candidate profile added successfully."

**Extensions**:
- 3a. Recruiter inputs invalid phone number or email.
  - System shows an error message: "Invalid phone number or email. Please enter valid information."
- 4a. A duplicate candidate profile is detected.
  - System shows an error message: "Duplicate candidate detected. Profile not added."

---

### Use Case 2: Search for Candidates by Criteria
**Actor**: HR Professional

**Preconditions**: Candidate profiles exist in the system.

**Main Success Scenario**:
1. HR professional selects the "Search" option.
2. System prompts HR professional to input search criteria (e.g., candidate name, role, or skill).
3. HR professional enters search criteria (e.g., role: Software Engineer).
4. System retrieves and displays a list of candidates matching the criteria.
5. HR professional selects a candidate from the list to view detailed information.

**Extensions**:
- 3a. No candidates match the search criteria.
  - System displays a message: "No candidates found for the given criteria."

---

## Non-functional Requirements

1. **Performance**:
  - The system should be able to handle up to 100 simultaneous users without a significant decrease in performance.
  - System response time for any operation should not exceed 1 second.

2. **Scalability**:
  - The system should support the management of up to 10,000 candidate profiles without performance degradation.
  - The system should be able to integrate with external recruitment systems (e.g., LinkedIn, job portals) to import candidate data.

3. **Usability**:
  - The system should be intuitive for users with basic computer skills.
  - The user interface should provide clear navigation, with minimal need for user training.

4. **Reliability**:
  - The system should have a 99.9% uptime, ensuring availability for recruiters and HR professionals during business hours.
  - Data should be backed up every 24 hours to prevent data loss.

5. **Security**:
  - The system must comply with GDPR regulations and ensure that candidate data is securely stored and handled.
  - All user data (including candidate information) should be encrypted both at rest and in transit.

6. **Portability**:
  - The system should be compatible with Windows, macOS, and Linux operating systems.
  - The system should be able to run on machines with at least 4GB of RAM and 2GHz processors.

7. **Accessibility**:
  - The system should comply with WCAG 2.1 standards for web accessibility, ensuring that users with disabilities can use the system effectively.



⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️⬇️
## Glossary

1. **Recruiter**:
  - A professional responsible for managing job applicants and their application process.

2. **HR Professional**:
  - A Human Resources professional responsible for managing employee information and candidate data during the recruitment process.

3. **Candidate Profile**:
  - A record containing all relevant details about a job applicant, including contact information, skills, experience, and interview notes.

4. **MVP (Minimum Viable Product)**:
  - The minimum version of a product that meets the basic requirements to be usable by the target audience.

5. **NFR (Non-functional Requirements)**:
  - Requirements that specify the quality and performance characteristics of the system (e.g., performance, security, usability).

6. **CRUD**:
  - Refers to the basic operations of Create, Read, Update, and Delete, typically applied to data management within an application.

7. **GDPR (General Data Protection Regulation)**:
  - A European regulation on data protection and privacy for individuals within the EU, applicable to the handling of personal data.



---

