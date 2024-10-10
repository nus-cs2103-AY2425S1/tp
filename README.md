# SalesContactPro

[![Java CI](https://github.com/AY2425S1-CS2103T-T17-4/tp/actions/workflows/gradle.yml/badge.svg)](https://github.com/AY2425S1-CS2103T-T17-4/tp/actions/workflows/gradle.yml)

![Ui](docs/images/Ui.png)

## Introduction

SalesContactPro is a CLI-first contact management system designed specifically for inside sales representatives in the Software-as-a-Service (SaaS) industry. This application aims to streamline contact management, enhance productivity, and optimize the sales process through rapid data entry and retrieval.

## Target Users

SalesContactPro is tailored for tech-savvy inside sales representatives in the sales industry who:

- Manage 200+ leads and customers
- Work remotely or in fast-paced office environments
- Prioritize speed and efficiency in their daily operations
- Are comfortable with command-line interfaces and prefer keyboard input
- Need quick access to contact information during calls or email communications
- Require efficient follow-up management and interaction tracking

## Value Proposition

SalesContactPro empowers SaaS inside sales reps with lightning-fast, CLI-driven contact management. It offers instant access to lead details, interaction histories, and follow-up schedules, maximizing call and email productivity for high-volume, rapid-paced sales environments.

## Features

### 1. Add Contact
- Command: `add /n <contact_name> /p <phone_number>`
- Quickly add new contacts with name and phone number

### 2. Delete Contact
- Command: `delete <contact_name>`
- Remove contacts from the system efficiently

### 3. Find Contact
- Command: `find <contact_name>`
- Rapidly search for and retrieve contact information

### 4. View Contacts
- Command: `view [/n NAME] [/t TAG] [/s SORT_FIELD] [/r] [/l LIMIT]`
- Display contacts with flexible filtering and sorting options

---

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
