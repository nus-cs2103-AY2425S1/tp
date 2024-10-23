[![Java CI](https://github.com/AY2425S1-CS2103T-T13-3/tp/actions/workflows/gradle.yml/badge.svg)](https://github.com/AY2425S1-CS2103T-T13-3/tp/actions/workflows/gradle.yml) [![codecov](https://codecov.io/gh/AY2425S1-CS2103T-T13-3/tp/graph/badge.svg?token=L39TRCVDDB)](https://codecov.io/gh/AY2425S1-CS2103T-T13-3/tp)

# PawPatrol: Veterinary Address Book and Patient Management System

![Ui](docs/images/Ui.png)
PawPatrol is designed to be a core tool for veterinary clinics to manage client and patient data efficiently. Its primary function is as an address book for pet owners, with extensions to handle detailed patient records for animals. The goal is to provide a lightweight but scalable system for veterinarians to track essential information, including medical history, appointments, and billing.

## Key Features

### Core Features

- **Pet Owner Contact Management**

  - Profiles: Capture basic owner info—name, contact details, address. Owners are linked to one or more pets.

- **Animal Patient Records**

  - Profile for Each Pet: Store relevant details—name, species, breed, age, weight.

- **Search Functionality**
  - Ability to search clients or pets by name, species, or other key data points.

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

1. Add a new pet owner: `owner add <n/ownerName> <hp/ownerNumber> <ad/ownerAddress> <e/ownerEmail>`
   Example:
   `owner add n/mr bobby hp/96667899 ad/3 lentil gardens e/bobby@gmail.com`

2. Add a new pet: `pet add <n/AnimalName> <s/Species> <b/Breed> <a/Age> <s/Sex> <o/owner>`
   Example:
   `pet add n/Tom s/Cat b/domestic shorthair cat a/7 s/male`

3. Search for clients or pets: `search <dataType> <searchValue>`
   Example:
   `search CLIENT mr bobby`

4. Delete an owner or pet: `delete <dataType> <index>`
   Example:
   `delete PET 2`

5. List owners or pets: `list <dataType>`
   Example:
   `list CLIENT`

## Contributors

This project was done by Aaron, Jordan, Clarissa, David and Jonathan.

## Acknowledgements

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
