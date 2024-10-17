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
     * It removes all future states in the history after the current state when a new change is saved.
     * This ensures that any redoable history is discarded once a new change is made after an undo.
     */
    public void save() {
        if (currentStatePointer < addressBookStateList.size() - 1) {
            addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
        }

        // Check if the current state is same as last saved state -> avoid saving a duplicate state
        if (!addressBookStateList.get(currentStatePointer).equals(new AddressBook(this))) {
            // Save the current state
            addressBookStateList.add(new AddressBook(this));
            currentStatePointer++;
        }
    }

    /**
     * Restores the previous state of the address book.
     * Moves one step back in the history to undo the last change.
     */
    public void undo() {
        if (canUndo()) {
            currentStatePointer--;
            resetData(addressBookStateList.get(currentStatePointer));
        }
    }

    /**
     * Checks if there is a previous state to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Restores the next state of the address book.
     * Moves one step forward in the history to redo the last undone change.
     */
    public void redo() {
        if (canRedo()) {
            currentStatePointer++;
            resetData(addressBookStateList.get(currentStatePointer));
        }
    }

    /**
     * Checks if there is a next state to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

}
