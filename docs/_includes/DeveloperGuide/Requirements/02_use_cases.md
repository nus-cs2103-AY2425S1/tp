### Use cases

(For all use cases below, the **System** is `SpleetWaise` and the **Actor** is the `user`, unless specified 
otherwise)

**UC01 - View Usage Instructions**

Actor: New User

**MSS**
1. The new user clicks on "Help" at top bar of the application.
2. The system displays usage instructions in a browser.
3. The user reviews the instructions.
   <br>Use case ends.<br>

**Extensions**
- **2a.** The user can switch between different sections of the instructions (e.g., FAQs, How-to sections, etc.).

---

**UC02 - Add a New Person**

**MSS**
1. A user request to add a new person with required details (e.g., name, email, phone number, address).
2. The system validates the input (e.g., checks for valid email format, non-empty fields).
3. The system saves the new person to the address book and displays a success message.
4. The new person is added to the list.
   <br>Use case ends.<br>

**Extensions**
- **2a.** The system detects an invalid email format or phone number.
  - 2a1. The system shows an error message.
  - 2a2. The user have to restart from step 1.

---

**UC03 - Delete a person**

**MSS**
1.  A user requests to list persons
2.  The system shows a list of persons
3.  The user requests to delete a specific person in the list
4.  The system deletes the person
   <br>Use case ends.<br>

**Extensions**
- **2a.** The list is empty.
  - 2a1. Use case ends.
- **3a.** The given index is invalid.
  - 3a1. AddressBook shows an error message.
  - 3a2. Use case resumes at step 2.

---

**UC04 - Find a Person by Name**

**MSS**
1. A user requests to search for a person by first or last name.
2. The system performs a search and displays matched person's details.
   <br>Use case ends.<br>

**Extensions**
- **2a.** The system finds no match.
  - 2a1. The system displays an empty list with the message of 0 persons listed.
  - 2a2. Use case ends.
- **2b.** The system finds multiple people with identical first or last names.
  - 2b1. The system displays the lists of found person along with a message for the user to specify which person.
  - 2b2. The user specify which exact person within the list using index.
  - 2b3. The system displays the specified person's details.
  - 2b4. Use case ends.

---

**UC05 - List all Person**

**MSS**
1. A user requests to list all person after searching for a specific person.
2. The system resets the displayed filtered list.
3. The system displays the list of all person.
   <br>Use case ends.<br>

---

**UC06 - Mark and Unmark Expenses**

**MSS**
1. A user requests to list expenses
2. The system shows a list of expenses
3. The user requests to mark or unmark the selected expense.
4. The system updates the status of the expense.
5. The system displays a success message indicating the change.
   <br>Use case ends.<br>

---

**UC07 - View Person's Owed Amount**

**MSS**
1. A user requests to list all person.
2. The user requests to filter transaction by a specific person in the list
3. The system displays how much the user owe to the selected person.
   <br>Use case ends.<br>

---

**UC08 - View Summary of Expenses and Balances**

**MSS**
1. A user view a summary of all expenses and balances.
2. The system calculates the total expenses, balances, and individual amounts owed whenever a transaction is successfully added
3. The system displays the summary to the user in a tabular format.
   <br>Use case ends.<br>

**Extensions**
- **2a.** The user requests a filtered summary (e.g., by date or category).
  - 2a1. The system filters the summary based on the user’s input.
  - 2a2. Use case resumes from step 3.

---

**UC09 - Filter Transactions**

**MSS**
1. A user requests to filter for a transaction by specific set of fields.
2. The system performs a search to find all transactions with matching details.
3. The system displays the list of filtered transactions.
   <br>Use case ends.<br>

**Extensions**
- **2a.** The system finds no match.
    - 2a1. The system displays an empty list of transaction.
    - 2a2. Use case ends.

---

**UC10 - List all Transactions**

**MSS**
1. A user requests to list all transaction after filtering.
2. The system resets the displayed filtered list.
3. The system displays the list of all transactions.
   <br>Use case ends.<br>
---
