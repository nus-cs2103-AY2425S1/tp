package seedu.address.model;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * An AddressBook that keeps track of version history
 * and supports undo and redo commands
 */
public class VersionedAddressBook extends AddressBook {
    private static final String MESSAGE_REDO_FAIL = "Redo failed: No next state to redo to!";
    private static final String MESSAGE_UNDO_FAIL = "Undo failed: No previous state to undo to!";

    private final List<ReadOnlyAddressBook> addressBookStateList;
    private final List<Predicate<? super Person>> predicateStateList;
    private int currentStatePointer;

    /**
     * Constructs a VersionedAddressBook using the Persons in the {@code toBeCopied}
     * Also initializes the first state as the data in {@code toBeCopied}
     */
    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        super(toBeCopied);

        addressBookStateList = new ArrayList<>();
        predicateStateList = new ArrayList<>();

        addressBookStateList.add(toBeCopied);
        predicateStateList.add(PREDICATE_SHOW_ALL_PERSONS);
        currentStatePointer = 0;
    }

    /**
     * Commits the data of {@code newState} to the version history.
     * @param newState New state of AddressBook after executing a command.
     */
    public void commit(ReadOnlyAddressBook newState, Predicate<? super Person> newPredicate) {
        assert newState != null : "New state cannot be null";
        assert newPredicate != null : "Predicate cannot be null";

        removeStatesAfterCurrentPointer();

        addressBookStateList.add(new AddressBook(newState));
        predicateStateList.add(newPredicate);
        currentStatePointer++;
    }

    /**
     * Returns the Predicate of the current state.
     * @return Predicate of the current state.
     */
    public Predicate<? super Person> getCurrentPredicate() {
        return predicateStateList.get(currentStatePointer);
    }

    /**
     * Reverts to the previous state of the address book.
     * @return Previous state of the address book.
     * @throws CommandException If there is no previous state to undo to.
     */
    public ReadOnlyAddressBook undo() throws CommandException {
        assert !addressBookStateList.isEmpty() : "State list should not be empty when undoing";
        if (currentStatePointer <= 0) {
            throw new CommandException(MESSAGE_UNDO_FAIL);
        }

        currentStatePointer--;
        return addressBookStateList.get(currentStatePointer);
    }

    /**
     * Advances to the next state of the address book.
     * @return Next state of the address book.
     * @throws CommandException If there is no next state to redo to.
     */
    /*
    public ReadOnlyAddressBook redo() throws CommandException {
        if (currentStatePointer >= addressBookStateList.size() - 1) {
            throw new CommandException(MESSAGE_REDO_FAIL);
        }

        currentStatePointer++;
        return addressBookStateList.get(currentStatePointer);
    }
    */

    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
        predicateStateList.subList(currentStatePointer + 1, predicateStateList.size()).clear();
    }
}
