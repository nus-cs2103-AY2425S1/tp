package spleetwaise.transaction.storage;


import java.util.Optional;

import spleetwaise.address.model.AddressBookModel;
import spleetwaise.address.model.person.Person;

/**
 * Contains utility methods used for the storage
 */
public class StorageUtil {
    private static AddressBookModel addressBookModel;

    public static void setAddressBookModel(AddressBookModel model) {
        addressBookModel = model;
    }

    public static Optional<Person> getPerson(String personId) {
        return addressBookModel.getPersonById(personId);
    }

}
