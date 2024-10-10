### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**UC01 - View Usage Instructions**

Actor: New User

**MSS**
1. The new user clicks on "Help" at top bar of the application.
2. The system displays usage instructions in a browser.
3. The user reviews the instructions.

**Extensions**
- **2a.** The user can switch between different sections of the instructions (e.g., FAQs, How-to sections, etc.).

---

**UC02 - Add a New Person**

**MSS**
1. A user request to add a new person with required details (e.g., name, email, phone number, address).
2. The system validates the input (e.g., checks for valid email format, non-empty fields).
3. The system saves the new person to the address book and displays a success message.
4. The new person is added to the list.

**Extensions**
- **4a.** The system detects an invalid email format or phone number.
  - 4a1. The system shows an error message.
  - 4a2. The user have to restart from step 1.

---

**UC03 - Delete a person**

**MSS**
1.  A user requests to list persons
2.  The system shows a list of persons
3.  The user requests to delete a specific person in the list
4.  The system deletes the person

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

**Extensions**
- **2a.** The system finds no match.
  - 2a1. The system displays a message indicating no results.
  - 2a2. Use case ends.
- **2b.** The system finds multiple people with identical first or last names.
  - 2b1. The system displays the lists of found person along with a message for the user to specify which person.
  - 2b2. The user specify which exact person within the list using index.
  - 2b3. The system displays the specified person's details.
  - 2b4. Use case ends.

---

**UC05 - Mark and Unmark Expenses**

**MSS**
1. A user requests to list expenses
2. The system shows a list of expenses
3. The user requests to mark or unmark the selected expense.
4. The system updates the status of the expense.
5. The system displays a success message indicating the change.

**Extensions**
- **3a.** The expense is a recurring expense.
  - 3a1. The system asks whether to apply the change to all future occurrences or just the current one.
  - 3a2. The user confirms.
  - 3a3. Use case resumes from step 4.

---

**UC06 - View Person's Owed Amount**

**MSS**
1. A user requests to list all person.
2. The user selects a person to view.
3. The system displays how much the user own to the selected person.

**Extensions**
- **3a.** The system includes currency conversion.
  - 3a1. The system converts the owed amount to a default currency.
  - 3a2. Use case resumes from step 3.

---

**UC07 - View Summary of Expenses and Balances**

**MSS**
1. A user chooses to view a summary of all expenses and balances.
2. The system calculates the total expenses, balances, and individual amounts owed.
3. The system displays the summary to the user in a tabular format.

**Extensions**
- **3a.** The user wants to see the summary in a graphical format.
  - 3a1. The system provides a graphical representation (e.g., pie charts, bar graphs).
  - 3a2. Use case resumes from step 3.
- **3b.** The user requests a filtered summary (e.g., by date or category).
  - 3b1. The system filters the summary based on the user’s input.
  - 3b2. Use case resumes from step 3.

---

**UC08 - Set Up Recurring Transactions**

**MSS**
1. A user requests to add an expense as a recurring transaction with required details (e.g., frequency, start date, end date).
2. The system sets up the recurring transaction and displays a confirmation message.

**Extensions**
- **2a.** The user requests a custom interval (e.g., bi-weekly).
  - 2a1. The system allows the user to specify a custom interval.
  - 2a2. Use case resumes from step 3.
- **2b.** The user requests reminders before the transaction is processed.
  - 2b1. The system sends reminders before the recurring transaction is processed.
  - 2b2. Use case ends.
