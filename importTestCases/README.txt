This folder contains suggested test cases for the import function. Feel free to edit the .csv files using a text editor or sheets editor (such as Google sheets) for manual testing of the import feature.

Expected behaviour
Base sheet: Imports all contacts
Duplicate entries: Skips duplicate contacts (same behaviour as attempting to add the same contact multiple times)
Missing Fields: Will attempt to fill in default values for contact info*, skips the contact if necessary fields are empty
Invalid Input: Attempts to replace invalid inputs for contact info*, skips the contact if necessary fields are invalid
Different order: Imports all contacts
Unnecessary column: Imports all contacts, ignores extraneous columns

*Contact info refer to either the email, telegram handle, phone number or module name. If any of these fields are invalid or empty, the app will keep them as empty
