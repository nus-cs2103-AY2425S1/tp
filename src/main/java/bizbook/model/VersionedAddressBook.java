package bizbook.model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This class represents the address book with versioning.
 */
public class VersionedAddressBook extends AddressBook {

    /* Stores the copy of the previous list of contacts */
    public final Deque<AddressBook> addressBookHistoryList = new ArrayDeque<>();

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        super(toBeCopied);
    }

    /**
     * Adds the current version of the {@code AddressBook} into a list.
     */
    public void commit() {
        // Limit the versions saved to be 5
        if (addressBookHistoryList.size() == 5) {
            addressBookHistoryList.removeFirst();
        }

        AddressBook addressBookCopy = new AddressBook();
        addressBookCopy.setPersons(getPersonList());
        addressBookCopy.setPinnedPersons(getPinnedPersonList());

        addressBookHistoryList.addLast(addressBookCopy);
    }

    /**
     * Reverts to the latest version of the {@code AddressBook} and removed it from the list.
     */
    public void undo() {
        AddressBook previousVersion = addressBookHistoryList.removeLast();
        setPersons(previousVersion.getPersonList());
        setPinnedPersons(previousVersion.getPinnedPersonList());
    }
}
