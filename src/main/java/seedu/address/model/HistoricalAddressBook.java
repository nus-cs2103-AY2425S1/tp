package seedu.address.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * A wrapper for individual address books produced by executing commands.
 * Facilitates undo/redo.
 */
public class HistoricalAddressBook implements ReadOnlyAddressBook {

    private static final int HISTORY_LIMIT = 200;

    private final Deque<ReadOnlyAddressBook> addressBookHistory;
    private final Deque<ReadOnlyAddressBook> addressBookUndoHistory;
    private final AddressBook currentAddressBook;

    /**
     * Constructor for a {@code HistoricalAddressBook} object.
     * Initializes while setting current data to the {@code ReadOnlyAddressBook} provided.
     * @param initialAddressBook The first recorded AddressBook
     */
    public HistoricalAddressBook(ReadOnlyAddressBook initialAddressBook) {
        addressBookHistory = new ArrayDeque<>();
        addressBookUndoHistory = new ArrayDeque<>();
        currentAddressBook = new AddressBook(initialAddressBook);
        addressBookHistory.add(currentAddressBook.copy());
    }

    /**
     * Constructor for a {@code HistoricalAddressBook} object.
     * Initializes with no data.
     */
    public HistoricalAddressBook() {
        addressBookHistory = new ArrayDeque<>();
        addressBookUndoHistory = new ArrayDeque<>();
        currentAddressBook = new AddressBook();
        addressBookHistory.add(currentAddressBook.copy());
    }

    /**
     * Constructor for a {@code HistoricalAddressBook} object.
     * Initializes all data with data given.
     */
    public HistoricalAddressBook(Deque<ReadOnlyAddressBook> addressBookHistory,
                                 Deque<ReadOnlyAddressBook> addressBookUndoHistory, AddressBook currentAddressBook) {
        this.addressBookHistory = new ArrayDeque<>();
        this.addressBookHistory.addAll(addressBookHistory);

        this.addressBookUndoHistory = new ArrayDeque<>();
        this.addressBookUndoHistory.addAll(addressBookUndoHistory);

        this.currentAddressBook = currentAddressBook.copy();
    }

    /**
     * Saves the current {@code AddressBook}
     * Clear the undo history
     */
    public void save() {
        addressBookUndoHistory.clear();
        addressBookHistory.add(currentAddressBook.copy());
        if (addressBookHistory.size() > HISTORY_LIMIT) {
            addressBookHistory.removeFirst();
        }
    }

    /**
     * Reverts the most recent changes
     */
    public void undo() {
        if (!canUndo()) {
            throw new RuntimeException("There is no command to undo.");
        }
        addressBookUndoHistory.add(addressBookHistory.removeLast());
        currentAddressBook.resetData(addressBookHistory.getLast());
    }

    /**
     * Reverses the most recent undo action
     */
    public void redo() {
        if (!canRedo()) {
            throw new RuntimeException("There is no command to redo.");
        }
        addressBookHistory.add(addressBookUndoHistory.removeLast());
        currentAddressBook.resetData(addressBookHistory.getLast());
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
        return !addressBookUndoHistory.isEmpty();
    }

    public AddressBook getCurrentAddressBook() {
        return currentAddressBook;
    }

    public Deque<ReadOnlyAddressBook> getAddressBookHistory() {
        return addressBookHistory;
    }

    public Deque<ReadOnlyAddressBook> getAddressBookUndoHistory() {
        return addressBookUndoHistory;
    }

    // ======================= Wrapper functions =========================

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     * Saves the current {@code Person} data into {@code Undo} history
     * before setting persons.
     * @see AddressBook#setPersons(List)
     */
    public void setPersons(List<Person> persons) {
        currentAddressBook.setPersons(persons);
        save();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     * Saves the current {@code Person} data into {@code Undo} history
     * before resetting data.
     * @see AddressBook#resetData(ReadOnlyAddressBook)
     */
    public void resetData(ReadOnlyAddressBook newData) {
        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     * @see AddressBook#hasPerson(Person)
     */
    public boolean hasPerson(Person person) {
        return currentAddressBook.hasPerson(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     * Saves the current {@code Person} data into {@code Undo} history
     * before adding the person.
     * @see AddressBook#addPerson(Person)
     */
    public void addPerson(Person p) {
        currentAddressBook.addPerson(p);
        save();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     * Saves the current {@code Person} data into {@code Undo} history
     * before setting the person.
     * @see AddressBook#setPerson(Person, Person)
     */
    public void setPerson(Person target, Person editedPerson) {
        currentAddressBook.setPerson(target, editedPerson);
        save();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     * Saves the current {@code Person} data into {@code Undo} history
     * before removing the person.
     * @see AddressBook#removePerson(Person)
     */
    public void removePerson(Person key) {
        currentAddressBook.removePerson(key);
        save();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return currentAddressBook.getPersonList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HistoricalAddressBook)) {
            return false;
        }

        HistoricalAddressBook otherHistoricalAddressBook = (HistoricalAddressBook) other;
        return CollectionUtil.isCollectionEqual(addressBookHistory, otherHistoricalAddressBook.addressBookHistory)
                && CollectionUtil.isCollectionEqual(addressBookUndoHistory,
                otherHistoricalAddressBook.addressBookUndoHistory)
                && currentAddressBook.equals(otherHistoricalAddressBook.currentAddressBook);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("addressBookHistory", addressBookHistory)
                .add("addressBookUndoHistory", addressBookUndoHistory)
                .add("currentAddressBook", currentAddressBook)
                .toString();
    }
}
