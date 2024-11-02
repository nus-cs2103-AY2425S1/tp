package bizbook.model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This class represents the address book with versioning.
 */
public class VersionedAddressBook extends AddressBook {

    /* Stores the copy of the previous list of contacts */
    public final Deque<AddressBook> addressBookVersionList = new ArrayDeque<>();

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        super(toBeCopied);
    }

    /**
     * Retrieves the list of the old address book versions.
     */
    public Deque<AddressBook> getAddressBookHistoryList() {
        return addressBookVersionList;
    }

    /**
     * Adds the current version of the {@code AddressBook} into the list.
     */
    public void commit() {
        // Limit the versions saved to be 5
        if (addressBookVersionList.size() == 5) {
            addressBookVersionList.removeFirst();
        }

        AddressBook addressBookCopy = new AddressBook();
        addressBookCopy.setPersons(getPersonList());
        addressBookCopy.setPinnedPersons(getPinnedPersonList());

        addressBookVersionList.addLast(addressBookCopy);
    }

    /**
     * Reverts to the latest version of the {@code AddressBook} and removed it from the list.
     */
    public void undo() {
        AddressBook previousVersion = addressBookVersionList.removeLast();
        setPersons(previousVersion.getPersonList());
        setPinnedPersons(previousVersion.getPinnedPersonList());
    }
}
