**Team Size: 5**

### 1. Enhance Usability of Search Feature

1.1 **Improve `find` Command for Partial Matching**  
Allow partial matches in `find` for various fields, including `names`, `phone numbers`, `emails`, `addresses`, `remarks`, and `tags`. This makes it easier for users to locate contacts even if they don’t have exact matches.

1.2 **Enhance `filterTxn` Command for Partial Matching Across Parameters**  
Explore partial matching in `filterTxn` for parameters like `amount`, `person`, `date` (day, month, year), `status`, and `category`, enabling users to search transactions with related keywords.

### 2. Enhance Specificity in Error Messages

2.1 **Improve Duplicate Transaction Error Message**  
Provide detailed information in duplicate transaction error messages, specifying duplicate fields (e.g., description, date, and amount) to help users resolve conflicts faster.

### 3. Enhance Input Validation

3.1 **International Phone Number Format Support**  
Update phone number validation to accept international formats, including country codes and special characters (e.g., "+", "-"). This allows users to store numbers in formats like "+65 91234567" or "+1-123-456-7890".

3.2 **Flexible Input Validation for Tags and Categories**  
Allow special characters in tags and categories, enabling users to create categories such as "#Business_Trip" or "Food & Beverage" for better organization.

3.3 **Enhanced Email Validation for Full Domain Structure**  
Enforce email validation to require a fully qualified domain (e.g., “username@example.com”) to prevent invalid formats, ensuring more reliable email entries.

### 4. Implement Duplicate Detection for Case-Insensitive Fields

4.1 **Case-Insensitive Duplicate Detection**  
Implement case-insensitive matching for fields like `names`, `emails`, and `tags`. Prompt users to confirm or discard duplicate entries if similar entries (e.g., “John Doe” and “john doe”) are detected.

### 5. UI Enhancements for Improved User Experience

5.1 **Custom App Icon**
Add custom app icon to enhance customizability.

5.2 **"Track Only Not Done Balance" Checkbox** 
Currently, users can calculate all balances that are "Not Done" via `filterTxn status\Not Done`. However, this does not allow users to display a filtered list that includes done transaction while showing only the "Not Done" balance. Adding a checkbox in balance section would give users the flexibility to track only "Not Done" balances.

5.3 **Display Empty List Message**
When the list is empty, the UI currently displays nothing. A message indicating that the list is empty would provide better clarity.

These enhancements address search functionality, error handling, input validation, duplicate detection, and user interface improvements, collectively enhancing SpleetWaise’s usability and functionality.
