### Project: AddressBook

AddressBook is an address book application that is designed for SME business owners who frequently procures goods from suppliers.

It is a one-stop solution for small business owners to keep track of their contracted suppliers, along with any other relevant business information such as purchased goods & services, dates and other potential waybill information.

#### Project contributions

- **New Feature**: Allow the tags of the supplier to reflect the goods category of their goods as well.
  - What it does: Shows the goods categories of the goods that the supplier has. (e.g. if alice has a goods that has a category of "consumables", then the alice card view will reflect a tag of "consumables".)
  - Justification: Allows (business owner) users to visually distinguish what type of goods a supplier is responsible for supplying. This  aids in the searching of supplier that sells a particular type of goods (especially used in conjunction with the find feature, which has been updated to filter by goods categories).
  - Highlights: The challenge was implementing this feature as simply as possible and ensuring that it remains as compatible as with the current architecture without introducing bugs. The final design decision was to utilise the facade pattern of the model, and focuses on using only one outgoing interface to the UI which resolves this very simply. It works via applying a combiner to two observable lists to transform into a single observable list for the UI.
- **Enhancements to existing features**:
  1. As part of the feature specification, amended the edit supplier and delete supplier command to use names as preambles rather than indices.
  2. Added cascading delete of supplier goods when the supplier is deleted.
  3. Updated the find command to filter by multiple goods categories as well.
- **Enhancements to new features**:
  1. Resolves a critical goods deletion bug where the deletion of goods would cause goods to be deleted for other suppliers as well. Supported in redesigning and refactoring the command usage and implementation.
  2. Resolves runtime errors during the addition of supplier goods. Supported in the refactoring of command parsing and execution related to supplier goods addition.
  3. Supported in improving the general quality of the code base. Like refactoring nulls to optionals, and applying a style from functional programming that allows for more concise and safer code.
- **Documentation**:
  - User Guide:
    - Updated feature added to commands.
  - Developer Guide:
    - Refurnished use cases.
    - Updated diagrams.
    - Updated implementations.
