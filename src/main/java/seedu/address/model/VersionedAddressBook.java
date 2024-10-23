package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the in-memory model of the address book data with versioning capabilities.
 * <p>
 * The VersionedAddressBook class extends the AddressBook class and adds the ability to track
 * the history of changes made to the address book. This allows for undo and redo operations.
 * <p>
 * The class maintains a list of address book states and a pointer to the current state.
 * The maximum number of states that can be stored is defined by the capacity.
 * When the capacity is exceeded, the oldest state is removed.
 * <p>
 * The class provides methods to commit the current state, undo the last change, and redo the last undone change.
 * It also provides methods to check if undo and redo operations are possible.
 */
public class VersionedAddressBook extends AddressBook {
    private final List<ReadOnlyAddressBook> addressBookStateList;
    private final int capacity = 11;
    private int currentStatePointer;

    /**
     * Constructs a VersionedAddressBook with an initial state.
     *
     * @param initialState The initial state of the address book.
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);
        addressBookStateList = new ArrayList<>(capacity);
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Commits the current state of the address book to the state list.
     * <p>
     * This method performs the following steps:
     * 1. Removes all states after the current state pointer.
     * 2. Adds the current state of the address book to the state list.
     * 3. Increments the current state pointer.
     * 4. If the state list exceeds the capacity, removes the oldest state and adjusts the state pointer.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
        if (addressBookStateList.size() > capacity) {
            addressBookStateList.remove(0);
            currentStatePointer--;
        }
    }

    /**
     * Removes all states after the current state pointer.
     */
    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Undo the last change to the address book.
     */
    public void undo() {
        if (canUndo()) {
            currentStatePointer--;
            resetData(addressBookStateList.get(currentStatePointer));
        }
    }

    /**
     * Redo the last undone change to the address book.
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
     * @return True if there are states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if there are states to redo.
     *
     * @return True if there are states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

    public int getCapacity() {
        return capacity;
    }
}
