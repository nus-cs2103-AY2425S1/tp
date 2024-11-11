### Use cases

(For all use cases below, the **System** is `SpleetWaise` and the **Actor** is the `user`, unless specified otherwise)

**UC01 - View Usage Instructions**

Actor: New User

**MSS**

1. The new user clicks on "Help" in the top bar of the application.
2. The system displays usage instructions in a browser.
3. The user reviews the instructions.<br>
   Use case ends.

**Extensions**

- **2a.** The user can switch between different sections of the instructions (e.g., FAQs, How-to sections).

---

**UC02 - Add a New Person**

**MSS**

1. The user requests to add a new person with the required details.
2. The system validates the input.
3. The system saves the new person to the address book and displays a success message.
4. The new person is added to the displayed address book list.<br>
   Use case ends.

**Extensions**

- **2a.** The system detects invalid input(s).
   - 2a1. The system shows an error message.<br>
     Use case restarts from step 1.

---

**UC03 - List All People**

**MSS**

1. The user requests to list all people.
2. The system displays the list of all people in the address book.<br>
   Use case ends.

---

**UC04 - Edit Details of a Person**

**MSS**

1. The user requests to <ins>list all people (UC03)</ins> or <ins>find a person (UC06)</ins>.
2. The user selects a person to edit and provides new details.
3. The system validates the input.
4. The system updates the person with the new details and displays a success message.
5. The edited person is updated and displayed in the address book.<br>
   Use case ends.

**Extensions**

- **1a.** The address book is empty.<br>
  Use case ends.

---

**UC05 - Delete a Person**

**MSS**

1. The user requests to <ins>list all people (UC03)</ins> or <ins>find a person (UC06)</ins>.
2. The user requests to delete a specific person.
3. The system deletes the person.<br>
   Use case ends.

**Extensions**

- **1a.** The address book is empty.<br>
  Use case ends.

---

**UC06 - Find Person(s)**

**MSS**

1. The user requests to search for a person by specific detail(s).
2. The system performs a search and displays all matching people.<br>
   Use case ends.

**Extensions**

- **2a.** The system finds no matches.
   - 2a1. The system displays an empty address book list.<br>
     Use case ends.
- **2b.** The system finds multiple people and displays them in the address book list.<br>
  Use case ends.

---

**UC07 - Add a New Transaction**

**MSS**

1. The user requests to <ins>list all people (UC03)</ins> or <ins>find a person (UC06)</ins>.
2. The user selects a person to add the new transaction with and inputs the required details (e.g., amount, date, description).
3. The system validates the input.
4. The system saves the new transaction in the transaction book and displays a success message.
5. The new transaction is added to the displayed transaction book.<br>
   Use case ends.

**Extensions**

- **2a.** The system detects invalid input(s).
   - 2a1. The system shows an error message.<br>
     Use case restarts from step 1.
- **4a.** The system detects a duplicate transaction.
   - 4a1. The system discards the duplicate and displays an error message.<br>
     Use case ends.

---

**UC08 - List All Transactions**

**MSS**

1. The user requests to list all transactions.
2. The system resets the displayed transaction book.
3. The system displays all transactions.<br>
   Use case ends.

---

**UC09 - Edit a Transaction**

**MSS**

1. The user requests to <ins>list all transactions (UC08)</ins> or <ins>filter transactions (UC12)</ins>.
2. The user selects a transaction to edit and provides new details.
3. The system validates the input.
4. The system updates the transaction with the new details and displays a success message.
5. The edited transaction is updated and displayed in the transaction book.<br>
   Use case ends.

**Extensions**

- **1a.** The transaction book is empty.<br>
  Use case ends.
- **3a.** The system detects invalid input(s).
   - 3a1. The system shows an error message.<br>
     Use case resumes from step 2.

---

**UC10 - Delete a Transaction**

**MSS**

1. The user requests to <ins>list all transactions (UC08)</ins> or <ins>filter transaction(s) (UC12)</ins>.
2. The user selects a specific transaction to delete.
3. The system deletes the transaction.<br>
   Use case ends.

**Extensions**

- **2a.** The transaction book is empty.<br>
  Use case ends.

---

**UC11 - Mark Transaction(s) as Done or Not Done**

**MSS**

1. The user requests to <ins>list all transactions (UC08)</ins> or <ins>filter transaction(s) (UC12)</ins>.
2. The user selects a specific transaction to mark as done or not done.
3. The system updates the transaction's status.
4. The system displays a success message indicating the status change.<br>
   Use case ends.

---

**UC12 - Filter Transaction(s)**

**MSS**

1. The user requests to filter transaction(s) by specific detail(s).
2. The system performs a search and displays all transactions with matching details.<br>
   Use case ends.

**Extensions**

- **2a.** The system finds no matches.
   - 2a1. The system displays an empty transaction list.<br>
     Use case ends.

---
