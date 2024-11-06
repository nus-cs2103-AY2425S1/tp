package bizbook.model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This class represents the address book with versioning.
 */
public class VersionedAddressBook extends AddressBook {

    /* Stores the copy of the previous list of contacts */
    public final Deque<AddressBook> addressBookOlderVersionList = new ArrayDeque<>();
    /* Stores the copy of the newer versions of the address book */
    public final Deque<AddressBook> addressBookNewerVersionList = new ArrayDeque<>();

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
        return addressBookOlderVersionList;
    }

    /**
     * Checks if there is a newer version that can be reverted to or not.
     */
    public boolean canRedo() {
        return addressBookNewerVersionList.size() > 0;
    }

    /**
     * Checks if there is a version that can be reverted to or not.
     */
    public boolean canUndo() {
        return addressBookOlderVersionList.size() > 1;
    }

    /**
     * Adds the current version of the {@code AddressBook} into the list.
     */
    public void commit() {
        AddressBook addressBookCopy = new AddressBook();
        addressBookCopy.setPersons(getPersonList());
        addressBookCopy.setPinnedPersons(getPinnedPersonList());
        // If equal to the previous version in the list then do not save
        if (!addressBookOlderVersionList.isEmpty() && addressBookCopy.equals(addressBookOlderVersionList.getLast())) {
            return;
        }
        // Limit the versions saved to be 5 but 1 slot will be for the base version
        if (addressBookOlderVersionList.size() == 6) {
            addressBookOlderVersionList.removeFirst();
        }
        addressBookOlderVersionList.addLast(addressBookCopy);

        addressBookNewerVersionList.clear();
    }

    /**
     * Reverts to the latest version of the {@code AddressBook} and removed it from the list.
     */
    public void undo() {
        AddressBook currentVersion = addressBookOlderVersionList.removeLast();
        AddressBook previousVersion = addressBookOlderVersionList.getLast();

        // Limit the versions saved to be 5
        if (addressBookNewerVersionList.size() == 5) {
            addressBookNewerVersionList.removeFirst();
        }

        addressBookNewerVersionList.addLast(currentVersion);

        setPersons(previousVersion.getPersonList());
        setPinnedPersons(previousVersion.getPinnedPersonList());
    }

    /**
     * Reverts to a newer version of the {@code AddressBook} and removed it from the list.
     */
    public void redo() {
        AddressBook newerVersion = addressBookNewerVersionList.removeLast();
        addressBookOlderVersionList.addLast(newerVersion);
        setPersons(newerVersion.getPersonList());
        setPinnedPersons(newerVersion.getPinnedPersonList());
    }
}
