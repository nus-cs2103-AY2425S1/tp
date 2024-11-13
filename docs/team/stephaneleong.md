---
layout: page
title: Leong Soon Mun Stephane's Project Portfolio Page
---

### Project: AddressBook Level 3

AddressBook - Level 3 is a desktop address book application used for teaching Software Engineering principles. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

### Contributions

1. Refactored Storage to Csv format
   - What it does: Allows the user to save and load data in a csv format.
   - Justification: This feature allows the user to save and load data in a more human readable format.
   - Highlights: This feature was implemented using the OpenCSV library.
2. Test Coverage for Goods Storage
   - What it does: Ensures that loading csv file to Goods Storage can handle duplicates, incorrect format, invalid fields, and other edge cases.
    - Justification: This feature ensures that the Goods Storage is robust and can handle edge cases, ensuring that the user's data is safe and does not corrupt the application.
    - Highlights: Tested rigorously using Junit to ensure that edge cases are handled.
3. Linking Goods to Supplier
   - What it does: When adding goods to supply central, the user can link the goods to a supplier.
   - Justification: This features when a good is added to supply central that a supplier is linked to it, allowing the user to easily track which supplier the good is from.
   - Highlights: This feature was implemented using java streams and lambda expressions.

