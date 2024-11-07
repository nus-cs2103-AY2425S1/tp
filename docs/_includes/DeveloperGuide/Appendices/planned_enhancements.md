Team Size: 5

### 1. Enhance Usability of Search Feature
    
Search related commands such as `find` and `filterTxn` could be more intuitive by allowing partial matches and relaxing constraints on keywords. For instance, we aim to improve `find` so users can find contacts based on partial matches for not just `names`, but also `phone numbers`,`email`, `addresses`, `remark`, `tags`, etc. Similarly, the `filterTxn` command currently supports exacts matches for filter by `amount`, `person`, `date`, `status`, and `category`; only `description` support partial matching. We plan to explore partial matching for other parameters such as the `day`, `month`, `year` in `date`, making it easier for users to find transactions with similar or related keywords.

### 2. Enhance Specificity in Error Message

Currently, error messages may lack specificity, such as in the case of duplicate transactions - stating "Transaction already exists in the transaction book" without further details. We plan to enhance error messages to provide clear reasons for the error, helping users correct errors faster. For example, specify "Transaction already exists in the transaction book: duplicate fields with description, date and amount."

### 3. Enhance Input Validation

- phone number validation will be updated to accept international formats, including country codes, spaces and special characters like "+" and "-". This will allow users to store numbers in formats such as "+65 91234567" or "+1-123-456-7890," facilitating the addition of more phone numbers.
- tags and categories could also benefit from more flexible input validation as they currently do not support special characters. We plan to extend input validation to allow a broader range of special characters in both tags and categories. For instance, users could use categories like "#Business_Trip" or categories like "Food & Beverage".
- email validation in SpleetWaise currently accepts formats that lack a full domain structure (e.g., "123@123"). To align with standard email validation rules, we plan to enhance email validation to enforce a fully qualified domain structure (e.g., “username@example.com”) to prevent invalid email formats. This will help users enter correct email addresses.

### 4. Implement Duplicate Detection for Supposed Case-Insensitive Fields

Currently, SpleetWaise treats inputs for fields such as `names`, `email`, `tags`, etc. as case-sensitive, which could lead to duplicates (e.g., “John Doe” and “john doe” are treated as separate entries). We plan to implement case-insensitive matching for such fields to be able to warn users of duplicate entries such that when a potential duplicate is detected based on case differences, we will provide a prompt allowing users to confirm or discard the duplicate entry.

### 5. UI Enhancements for Improved User Experience
To make SpleetWaise more intuitive and visually appealing, we plan to implement several UI enhancements. These updates will include clearer differentiator for transaction statuses (e.g., green for done, red for not done), and optimised spacing/padding/margin for readability. Additionally, we will improve the layout of contact and transaction details by grouping related fields or adding field labels such as for remark, making information easier to locate and review. These enhancements aim to create a more user-friendly interface.

These enhancements aim to improve usability, error handling, and functionality, enhancing the overall SpleetWaise experience for users.
