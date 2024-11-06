#### `AddressBookModel`

**API** : [`AddressBookModel.java`](https://github.com/AY2425S1-CS2103-F13-1/tp/blob/master/src/main/java/spleetwaise/address/model/AddressBookModel.java)

<img src="images/ModelClassDiagram.png" width="450" />

The  `AddressBookModel` component:

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

#### `TransactionBookModel`

**API** : [`TransactionBookModel.java`](https://github.com/AY2425S1-CS2103-F13-1/tp/blob/master/src/main/java/spleetwaise/transaction/model/TransactionBookModel.java)

<img src="images/TransactionModelClassDiagram.png" width="450" />

- stores the transaction book data i.e., all `Transaction` objects (which are contained in a `ObservableList<Transaction>` object).
- stores the currently 'selected' `Transaction` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Transaction>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)


#### `CommonModel`

**API** : [`CommonModel.java`](https://github.com/AY2425S1-CS2103-F13-1/tp/blob/master/src/main/java/spleetwaise/commons/model/CommonModel.java)

<img src="images/CommonModelClassDiagram.png" width="450" />

The `CommonModel` component:

- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.

#### `CommonModelManager`

Singleton `CommonModel` class. The singleton instance of this class contains an instance of both `AddressBookModel` and `TransactionBookModel`. The singleton instance acts as a facade, exposing the APIs of both `AddressBookModel` and `TransactionBookModel`.

Certain transaction-related features need access to data from both the address and transaction book from different areas of the codebase. For this reason, we decided to go with the `CommonModelManager` class design described above.
