**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103-F13-1/tp/blob/master/src/main/java/spleetwaise/commons/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550"  alt="Storage Class Diagram"/>

The `Storage` component,

* can save address book data, transaction book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from `AddressBookStorage`, `TransactionBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)
