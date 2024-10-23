package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AddressBook} that tracks its state across command executions
 */
public class VersionedAddressBook extends AddressBook {
    private final List<ReadOnlyAddressBook> addressBookStates;
    private int currentStatePointer;

    /**
     * Constructs a {@code VersionedAddressBook} with the initial state of the address book.
     * The address book will start with the specified initial state and the current state pointer
     * will be set to the initial state.
     *
     * @param initialState The initial state of the address book to be used as the starting point.
     *                     Cannot be null.
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStates = new ArrayList<>();
        addressBookStates.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

}
