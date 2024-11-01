package bizbook.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.stream.Collectors;

import bizbook.model.person.Person;

/**
 * This class represents the address book with versioning.
 */
public class VersionedAddressBook extends AddressBook {

    public final Deque<ArrayList<Person>> addressBookHistoryList;

    public VersionedAddressBook() {
        this.addressBookHistoryList = new ArrayDeque<>();
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        super.resetData(toBeCopied);
    }

    /**
     * Adds the current version of the {@code AddressBook} into a list.
     */
    public void commit() {
        // Limit the versions saved to be 5
        if (addressBookHistoryList.size() == 5) {
            addressBookHistoryList.removeFirst();
        }

        ArrayList<Person> copy = new ArrayList<>(this.getPersonList().stream().collect(Collectors.toList()));

        addressBookHistoryList.addLast(copy);
    }

    /**
     * Reverts to the latest version of the {@code AddressBook} and removed it from the list.
     */
    public void undo() {
        this.setPersons(addressBookHistoryList.removeLast());
    }
}
