package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * VersionedAddressBook is an extension of the AddressBook that supports undo/redo functionality.
 * It stores a history of AddressBook states and allows for reverting to previous states or restoring
 * undone states.
 */
public class VersionedAddressBook extends AddressBook {
    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    /**
     * Creates a VersionedAddressBook with the initial state.
     *
     * @param initialState The initial state of the AddressBook.
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);
        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves the current address book state to the state list.
     */
    public void save() {
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
    }

    /**
     * Restores the previous state from the state list.
     */
    public void undo() {
        if (canUndo()) {
            currentStatePointer--;
            resetData(addressBookStateList.get(currentStatePointer)); // Restore previous state
        }
    }

    /**
     * Returns true if undo is possible.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

}
