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
    // TODO: Update undo and redo fail messages
    private static final String MESSAGE_REDO_FAIL = "Redo fail";
    private static final String MESSAGE_UNDO_FAIL = "Undo fail";

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
        removeStatesAfterCurrentPointer();

        addressBookStateList.add(new AddressBook(newState));
        predicateStateList.add(newPredicate);
        currentStatePointer++;
    }

    public Predicate<? super Person> getCurrentPredicate() {
        return predicateStateList.get(currentStatePointer);
    }

    public ReadOnlyAddressBook undo() throws CommandException {
        if (currentStatePointer <= 0) {
            throw new CommandException(MESSAGE_UNDO_FAIL);
        }

        currentStatePointer--;
        return addressBookStateList.get(currentStatePointer);
    }

    public ReadOnlyAddressBook redo() throws CommandException {
        if (currentStatePointer >= addressBookStateList.size() - 1) {
            throw new CommandException(MESSAGE_REDO_FAIL);
        }

        currentStatePointer++;
        return addressBookStateList.get(currentStatePointer);
    }

    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
        predicateStateList.subList(currentStatePointer + 1, predicateStateList.size()).clear();
    }
}
