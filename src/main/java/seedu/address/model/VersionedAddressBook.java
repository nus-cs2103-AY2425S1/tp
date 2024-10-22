package seedu.address.model;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * An AddressBook that keeps track of version history
 * and supports undo and redo commands
 */
public class VersionedAddressBook extends AddressBook {
    private static final String MESSAGE_REDO_FAIL = "";
    private static final String MESSAGE_UNDO_FAIL = "";

    private final ArrayList<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    /**
     * Constructs a VersionedAddressBook using the Persons in the {@code toBeCopied}
     * Also initializes the first state as the data in {@code toBeCopied}
     */
    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        super(toBeCopied);
        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(toBeCopied);
        currentStatePointer = 0;
    }

    /**
     * Commits the data of {@code newState} to the version history.
     * @param newState New state of AddressBook after executing a command.
     */
    public void commit(ReadOnlyAddressBook newState) {
        // Clear all states after the current index
        if (currentStatePointer != addressBookStateList.size() - 1) {
            addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
        }

        addressBookStateList.add(newState);
        currentStatePointer++;
    }

    /**
     * Reverts pointer to previous state and returns the state
     * @return TODO: FILL LATER
     * @throws CommandException TODO: FILL LATER
     */
    public ReadOnlyAddressBook undo() throws CommandException {
        if (currentStatePointer > 0) {
            currentStatePointer--;
            return addressBookStateList.get(currentStatePointer);
        } else {
            throw new CommandException(MESSAGE_UNDO_FAIL);
        }
    }

    /**
     * TODO: fill later
     * @return e
     * @throws CommandException e
     */
    public ReadOnlyAddressBook redo() throws CommandException {
        if (currentStatePointer < addressBookStateList.size() - 1) {
            currentStatePointer++;
            return addressBookStateList.get(currentStatePointer);
        } else {
            throw new CommandException(MESSAGE_REDO_FAIL);
        }
    }
}
