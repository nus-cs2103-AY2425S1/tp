package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an address book that maintains a history of its states to support undo and redo operations.
 */
public class VersionedAddressBook extends AddressBook {
    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    /**
     * Creates a VersionedAddressBook with the initial state.
     *
     * @param initialState The initial state of the address book.
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);
        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves the current state of the address book to the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
    }

    /**
     * Removes states after the current pointer to maintain the correct history.
     */
    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (canUndo()) {
            currentStatePointer--;
            resetData(addressBookStateList.get(currentStatePointer));
        }
    }

    /**
     * Restores the address book to its next state.
     */
    public void redo() {
        if (canRedo()) {
            currentStatePointer++;
            resetData(addressBookStateList.get(currentStatePointer));
        }
    }

    /**
     * Returns true if there are states to undo.
     *
     * @return True if there are states to undo, false otherwise.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if there are states to redo.
     *
     * @return True if there are states to redo, false otherwise.
     */
    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }
}
