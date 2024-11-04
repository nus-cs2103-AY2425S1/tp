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
        this.commit();
    }

    /**
     * Retrieves the list of the old address book versions.
     */
    public Deque<AddressBook> getAddressBookHistoryList() {
        return addressBookVersionList;
    }

    /**
     * Checks if there is a version that can be reverted to or not.
     */
    public boolean canUndo() {
        return addressBookVersionList.size() > 1;
    }

    /**
     * Adds the current version of the {@code AddressBook} into the list.
     */
    public void commit() {
        AddressBook addressBookCopy = new AddressBook();
        addressBookCopy.setPersons(getPersonList());
        addressBookCopy.setPinnedPersons(getPinnedPersonList());
        // If equal to the previous version in the list then do not save
        if (!addressBookVersionList.isEmpty() && addressBookCopy.equals(addressBookVersionList.getLast())) {
            return;
        }
        // Limit the versions saved to be 5 but 1 slot will be for the base version
        if (addressBookVersionList.size() == 6) {
            addressBookVersionList.removeFirst();
        }
        addressBookVersionList.addLast(addressBookCopy);
    }

    /**
     * Reverts to the latest version of the {@code AddressBook} and removed it from the list.
     */
    public void undo() {
        addressBookVersionList.removeLast();
        AddressBook previousVersion = addressBookVersionList.getLast();
        setPersons(previousVersion.getPersonList());
        setPinnedPersons(previousVersion.getPinnedPersonList());
    }
}
