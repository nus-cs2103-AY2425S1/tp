[![CI Status](https://github.com/AY2425S1-CS2103T-T14-3/tp/workflows/Java%20CI/badge.svg)](https://github.com/AY2425S1-CS2103T-T14-3/tp/actions)

[![codecov](https://codecov.io/gh/AY2425S1-CS2103-T14-3/tp/branch/master/graph/badge.svg)](https://codecov.io/gh/AY2425S1-CS2103-T14-3/tp/)

# MATER - Car Workshop Management System

![Ui](docs/images/Ui.png)



MATER (Mechanical Assistance Tracker for Efficient Repairs) is a car workshop management system designed to streamline the process of tracking vehicles, handling issues, and managing bookings. It helps workshop staff keep track of customer cars, issues (both current and past), and allows querying for common issues by vehicle.

# Features
### 1. Add Client

Onboard new clients into the system by providing basic details such as name, phone number, email address, and home address. Car details like vehicle registration number, VIN, make, and model are added during client creation.


Command: `add-client n/NAME p/PHONE_NUMBER e/EMAIL_ADDRESS a/HOME_ADDRESS vrn/VEHICLE_REGISTERATION_NUMBER vin/VEHICLE_IDENTIFICATION_NUMBER make/VEHICLE_MAKE model/VEHICLE_MODEL`


### Delete Client

Remove a client's profile from the system by specifying their unique client ID.

Command: `del-client CLIENT_ID`


### Edit Client

Edit a client's profile, including personal information and car details.
Command: `edit CLIENT_ID FIELD NEW_VALUE`
### List Clients

View a list of clients with details, limiting the number of entries returned based on usage needs.
Command: `list LIMIT`
### View Client

View a specific client's profile by querying their unique client ID.
Command: `view CLIENT_ID`


* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).
