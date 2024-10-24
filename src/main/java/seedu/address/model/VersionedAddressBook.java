package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code VersionedAddressBook} stores multiple versions of {@code AddressBook} state,
 * enabling undo and redo functionality.
 */
public class VersionedAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    /**
     * Initializes a new {@code VersionedAddressBook} with the initial state.
     *
     * @param initialState The initial state of the address book.
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        AddressBook newState = new AddressBook(initialState);
        addressBookStateList.add(newState);
        currentStatePointer = 0;
    }

    /**
     * Saves the current state of the address book.
     * Clears any redo history when called after undo.
     */
    public void save() {
        // Clear all states after the current pointer to prevent redo states
        clearRedoStack();

        AddressBook newState = new AddressBook(this);
        addressBookStateList.add(newState);
        currentStatePointer++;
    }

    /**
     * Restores the {@code AddressBook} to the previous state.
     *
     * @throws InvalidUndoException if there is no state to undo to.
     */
    public void undo() {
        if (canUndo()) {
            currentStatePointer--;
            ReadOnlyAddressBook previousState = addressBookStateList.get(currentStatePointer);
            resetData(previousState);
            return;
        }
        throw new InvalidUndoException();
    }

    /**
     * Restores the {@code AddressBook} to the next undone state.
     *
     * @throws InvalidRedoException if there is no state to redo to.
     */
    public void redo() {
        if (canRedo()) {
            currentStatePointer++;
            ReadOnlyAddressBook nextState = addressBookStateList.get(currentStatePointer);
            resetData(nextState);
            return;
        }
        throw new InvalidRedoException();
    }

    /**
     * Clears all states after the current state to prevent redo.
     * This is called when a new state is saved after an undo.
     */
    private void clearRedoStack() {
        // Remove all states beyond the current pointer
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Returns {@code true} if there are states to undo.
     *
     * @return {@code true} if undo is possible, {@code false} otherwise.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns {@code true} if there are states to redo.
     *
     * @return {@code true} if redo is possible, {@code false} otherwise.
     */
    public boolean canRedo() {
        int sizeOfStateList = addressBookStateList.size() - 1;
        return currentStatePointer < sizeOfStateList;
    }

    /**
     * Exception thrown when there is no state to undo to.
     */
    public static class InvalidUndoException extends RuntimeException {
        public InvalidUndoException() {
            super("No available states to undo to.");
        }
    }

    /**
     * Exception thrown when there is no state to redo to.
     */
    public static class InvalidRedoException extends RuntimeException {
        public InvalidRedoException() {
            super("No available states to redo to.");
        }
    }
}
