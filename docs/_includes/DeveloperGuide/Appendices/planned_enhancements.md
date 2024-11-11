**Team Size: 5**

0. **Enhancements for Tracking “Not Done” Balances**<br>
   Currently, users can calculate all balances that are "Not Done" via `filterTxn status/Not Done`. However, this doesn’t provide the option to display a filtered transactions list that includes completed transactions while only showing the balance for “Not Done” transactions. Additionally, introducing a command like `showBalance status/Not Done` would give users a streamlined way to view balances for only “Not Done” transactions, regardless of the state of the filtered list. This command would allow users to easily toggle between viewing total balances and those specifically marked as “Not Done.”

0. **Case-Insensitive Duplicate Detection for Person**<br>
   SpleetWaise currently treats names as case-sensitive, so persons with names like "bob" and "Bob" are stored as distinct entries, even with the same phone number. This can lead to duplicate entries if capitalisation is inconsistent (e.g., “Alex Yeoh” vs. “alex yeoh”). Future updates aim to handle fields like `names`, `emails`, `tags` and `remarks` in a case-insensitive manner, treating persons with identical names with matching phone numbers as potential duplicates and alerting the user to confirm or discard the entries.

0. **Case-Insensitive Duplicate Detection for Transaction**<br>
   Currently, transaction with `addTxn 1 amt/12 desc/ntuc fairprice` and `addTxn 1 amt/12 desc/NTUC FAIRPRICE` is allowed without a warning/confirmation/alert to users even though it could be a potential duplicate (notice that the description in both the commands are treated as different). We aim to implement case-insensitive matching for the `description` field to detect potential duplicates of transaction and prompt users to confirm or discard duplicate entries.

0. **Improve `find` Command for Partial Matching Across Parameters**<br>
   Allow `find` command to search for persons through more fields and implement partial matches for `names`, `phone numbers`, `emails`, `addresses`, `remarks`, and `tags`. This makes it easier for users to locate contacts even if they don’t have exact matches.

0. **Enhance `filterTxn` Command for Partial Matching Across Parameters**<br>
   Implement partial matching in `filterTxn` for parameters like `amount`, `person`, `date` (day, month, year), `status`, and `category`, enabling users to search transactions with related keywords without exact matches.

0. **International Phone Number Format Support**<br>
   Update phone number validation to accept international formats, including country codes and special characters (e.g., "+", "-"). This allows users to store numbers in formats like "+65 91234567" or "+1-123-456-7890".

0. **Flexible Input Validation for Tags**<br>
   Allow special characters in tags,  enabling users to create tags such as "#Business_Partner" or "Friends & Family" for better organization.

0. **Flexible Input Validation for Categories**<br>
   Allow special characters in categories, enabling users to create categories such as "#Business_Trip" or "Food & Beverage" for better organization.

0. **Enhanced Email Validation for Full Domain Structure**<br>
   Enforce email validation to require a fully qualified domain (e.g., “username@example.com”) to prevent invalid formats, ensuring more reliable email entries.

0. **Improve Duplicate Transaction Error Message**<br>
   Provide detailed information in duplicate transaction error messages, specifying duplicate fields (e.g., description, date, and amount) to help users resolve conflicts faster.

These enhancements address search functionality, error handling, input validation, duplicate detection, and user interface improvements, collectively enhancing SpleetWaise’s usability and functionality.
