### Glossary

This Spleetwaise app is a _single-user_ application. Transactions are relative to <ins>_the user_</ins>.
- **Mainstream OS**: Windows, Linux, Unix, macOS
- **Private contact detail**: A contact detail that is not meant to be shared with others
- **Transaction**: A Transaction represents a record of a financial interaction between <ins>_the user_</ins> and another party (another contact).
  - **Undone** Transaction: By default, a newly created transaction is set as undone - _e.g._ if the transaction is added as `addTxn 1 amt/12.3 desc/John owes me for dinner`, this transaction is not done, John still owes <ins>_the user_</ins>.
  - **Done** Transaction: A completed transaction, referring to the previous _e.g._ once John has paid <ins>_the user_</ins>, he will mark the transaction as done.
