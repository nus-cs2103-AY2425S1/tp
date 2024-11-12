# Nom Nom Notifier

[![CI Status](https://github.com/AY2425S1-CS2103T-T13-2/tp/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/AY2425S1-CS2103T-T13-2/tp/actions)

![Ui](docs/images/Ui.png)
## Introduction

Nom Nom Notifier is a command-line application with a graphical interface designed to streamline customer management for small eateries and restaurants. It allows restaurant teams to efficiently manage customer details for delivery and service purposes.

## Features
- **Add**: Add new customer details such as name, phone number, email, address, and postal code. Optionally, add tags for further categorization.
- **Delete**: Remove customer details by index.
- **Clear**: Remove all customers from the address book.
- **Edit**: Modify existing customer details.
- **List**: Display all customers in the address book.
- **Download**: Export customer details to a CSV file.
- **Find**: Find a customer by name, number, email address, address, postal code or tags.
- **Help**: Display help information.
- **Exit**: Close the application.

## Usage
After running the application, you can enter commands to manage your address book. Below is a list of commands and their usage.

## Commands
- **Add a Contact**
    ```
    add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS pc/POSTAL_CODE [t/TAG]…
    ```
  Example:
    ```
    add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 pc/666234
    ```
- **Delete a Contact**
  ```
  delete INDEX
  ```
  Example:
  ```
  delete 1
  ```
- **Find a Contact**
    ```
    find KEYWORD [MORE_KEYWORDS]
    ```
  Example:
    ```
    find James Jake
    ```
- **Edit a Contact**
    ```
    edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [pc/POSTAL_CODE] [t/TAG]…
    ```
  Example:
    ```
    edit 1 n/John Doe p/98765432
    ```
- **Download Contacts**
    ```
    download [t/TAG1] [t/TAG2] …
    ```
    Example:
  ```
  download t/vegan
  ```
- **Help**
    ```
    help
    ```
- **List all Contacts**
  ```
  list
  ```
- **Clear all Contacts**
    ```
    clear
    ```
- **Exit the Application**
    ```
    exit
    ```
---
This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
