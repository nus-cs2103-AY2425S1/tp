[![CI Status](https://github.com/AY2425S1-CS2103T-T14-2/tp/workflows/Java%20CI/badge.svg)](https://github.com/AY2425S1-CS2103T-T14-2/tp/actions)

![Ui](docs/images/Ui.png)

# UniNet

UniNet helps international students in NUS manage detail of their contacts. It is optimized for CLI users so that frequent tasks can be done faster by typing in commands.

# Features List:

1. [Add contacts](#add-contacts)
2. [View all contacts](#view-contacts)
3. [Delete contacts](#delete-contacts)
4. [Prepopulated contacts](#prepopulated-contacts)

## Add contacts

Purpose: Allows the user to add a new contactMV to their list.

Command format: `add NAME PHONE_NUMBER [EMAIL] [TAG]`

Example commands:

- `add John Doe 98765432`

- `add Alice Lee 12345678 alicelee@example.com Friend`

## View all contacts

Purpose: Display all saved contacts in a single view.

Command format: `all`

For each parameter:

- No parameters.

## Delete contacts

Purpose: Allows the user to delete an existing contact.

Command format: `delete <INDEX>`  
Example commands:

- `delete 1` (deletes the first contact in the list)
- `delete 3` (deletes the third contact in the list)

## Prepopulated contacts

Purpose: Assists international students by providing a prepopulated database of useful contacts. We believe that this feature would provide much value to international students especially.

Command format: N/A

# Credits

- This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
