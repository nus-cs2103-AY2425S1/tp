package seedu.address.model;

import java.util.Stack;

/**
 * An Address Book that remembers its history.
 */
public class HistoricalAddressBook extends AddressBook {

    private final Stack<ReadOnlyAddressBook> addressBookHistory;
    private final Stack<ReadOnlyAddressBook> addressBookUndoHistory;

    /**
     * Constructor for a {@code HistoricalAddressBook} object
     * @param initialAddressBook The first recorded AddressBook
     */
    public HistoricalAddressBook(ReadOnlyAddressBook initialAddressBook) {
        super(initialAddressBook);

        addressBookHistory = new Stack<>();
        addressBookUndoHistory = new Stack<>();
        addressBookHistory.push(new AddressBook(this));
    }

    /**
     * Saves the current {@code AddressBook}
     * Clear the undo history
     */
    public void save() {
        addressBookUndoHistory.clear();
        addressBookHistory.push(new AddressBook(this));
    }

    /**
     * Reverts the most recent changes
     */
    public void undo() {
        if (!canUndo()) {
            throw new RuntimeException("There is no command to undo.");
        }
        addressBookUndoHistory.push(addressBookHistory.pop());
        resetData(addressBookHistory.peek());
    }

    /**
     * Reverses the most recent undo action
     */
    public void redo() {
        if (!canRedo()) {
            throw new RuntimeException("There is no command to redo.");
        }
        addressBookHistory.push(addressBookUndoHistory.pop());
        resetData(addressBookHistory.peek());
    }

    /**
     * Checks whether it is possible to perform undo operation
     */
    public boolean canUndo() {
        return addressBookHistory.size() > 1;
    }

    /**
     * Checks whether it is possible to perform redo operation
     */
    public boolean canRedo() {
        return addressBookUndoHistory.size() > 0;
    }
}
