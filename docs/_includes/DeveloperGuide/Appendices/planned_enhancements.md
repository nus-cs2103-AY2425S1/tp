**Team Size: 5**


0. **Improve `find` Command for Partial Matching**
Allow partial matches in `find` for various fields, including `names`, `phone numbers`, `emails`, `addresses`, `remarks`, and `tags`. This makes it easier for users to locate contacts even if they don’t have exact matches.

0. **Enhance `filterTxn` Command for Partial Matching Across Parameters**
Explore partial matching in `filterTxn` for parameters like `amount`, `person`, `date` (day, month, year), `status`, and `category`, enabling users to search transactions with related keywords.

0. **Improve Duplicate Transaction Error Message**
Provide detailed information in duplicate transaction error messages, specifying duplicate fields (e.g., description, date, and amount) to help users resolve conflicts faster.

0. **International Phone Number Format Support**
Update phone number validation to accept international formats, including country codes and special characters (e.g., "+", "-"). This allows users to store numbers in formats like "+65 91234567" or "+1-123-456-7890".

0. **Flexible Input Validation for Tags and Categories**
Allow special characters in tags and categories, enabling users to create categories such as "#Business_Trip" or "Food & Beverage" for better organization.

0. **Enhanced Email Validation for Full Domain Structure**
Enforce email validation to require a fully qualified domain (e.g., “username@example.com”) to prevent invalid formats, ensuring more reliable email entries.

0. **Case-Insensitive Duplicate Detection**
Implement case-insensitive matching for fields like `names`, `emails`, and `tags`. Prompt users to confirm or discard duplicate entries if similar entries (e.g., “John Doe” and “john doe”) are detected.

0. **Transaction Status Differentiation and Spacing Improvements**
Add color indicators for transaction statuses (e.g., green for done, red for not done) and optimize spacing for readability. Group related fields and add labels to improve information layout, making it easier for users to navigate and review details.

0. **Add path to `transactionbook.json` in the footer bar.** Currently, only the path of `addressbook.json` is displayed in the footer bar. Add the path to `transactionbook.json` as well for users to easily locate.

0. **Custom App Icon**
Add custom app icon to enhance customizability.

0. **"Track Only Not Done Balance" Checkbox**
Currently, users can calculate all balances that are "Not Done" via `filterTxn status\Not Done`. However, this does not allow users to display a filtered list that includes done transaction while showing only the "Not Done" balance. Adding a checkbox in balance section would give users the flexibility to track only "Not Done" balances.

0. **Add empty list "No [persons/transactions] in list" placeholder.** Add a placeholder message to person and transaction list views to indicate that there are no entries being rendered.

These enhancements address search functionality, error handling, input validation, duplicate detection, and user interface improvements, collectively enhancing SpleetWaise’s usability and functionality.
