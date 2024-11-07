[![Java CI](https://github.com/AY2425S1-CS2103T-T13-3/tp/actions/workflows/gradle.yml/badge.svg)](https://github.com/AY2425S1-CS2103T-T13-3/tp/actions/workflows/gradle.yml) [![codecov](https://codecov.io/gh/AY2425S1-CS2103T-T13-3/tp/graph/badge.svg?token=L39TRCVDDB)](https://codecov.io/gh/AY2425S1-CS2103T-T13-3/tp)

# PawPatrol: Veterinary Address Book and Patient Management System

![Ui](docs/images/Ui.jpeg)
PawPatrol is designed to be a core tool for veterinary clinics to manage client and patient data efficiently. Its primary function is as an address book for pet owners, with extensions to handle detailed patient records for animals. The goal is to provide a lightweight but scalable system for veterinarians to track essential information, including medical history, appointments, and billing.

## Key Features

### Core Features

- **Pet Owner Contact Management**

  - Profiles: Capture basic owner info—name, ic number, contact details, address. Owners are linked to one or more pets.

- **Animal Patient Records**

  - Profile for Each Pet: Store relevant details—name, species, breed, age, sex.

- **Search Functionality**
  - Ability to search clients or pets by name.

## Technologies Used

- Java
- JavaFX
- HTML
- CSS
- Git

## Installation Instructions

To install and run PawPatrol, follow these steps:
TO BE UPDATED

## How to Use the Project

1. Add a new pet owner: `owner i/IC_NUMBER n/NAME p/NUMBER a/ADDRESS e/EMAIL`  
   Example:
   `owner i/S1234567D n/mr bobby p/96667899 a/3 lentil gardens e/bobby@gmail.com`

2. Add a new pet: `pet n/NAME s/SPECIES b/BREED a/AGE s/SEX`  
   Example:
   `pet n/Tom s/Cat b/domestic shorthair cat a/7 s/male`

3. Search for clients or pets: `find DATA_TYPE KEYWORD [MORE_KEYWORDS]`  
   Example:
   `find owner mr bobby`

4. Link owners and pets: `link oOWNER_INDEX t/pPET_INDEX [t/pPET_INDEX]`  
   Example:
   `link o1 t/p1 t/p2`

5. Delete an owner or pet: `delete oOWNER_INDEX` / `delete pPET_INDEX`  
   Example:
   `delete p2`

6. List owners or pets: `list DATA_TYPE`  
   Example:
   `list owners` / `list pets` / `list` (to list both)

7. Sort owners or pets alphabetically: `sort DATA_TYPE`  
   Example: 
   `sort owners` / `sort pets`

## Contributors

This project was done by Aaron, Jordan, Clarissa, David and Jonathan.

## Acknowledgements

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
