package seedu.address.model;

import java.util.List;
import java.util.Stack;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;

/**
 * A wrapper for individual address books produced by executing commands.
 * Facilitates undo/redo.
 */
public class HistoricalAddressBook implements ReadOnlyAddressBook {

    private final Stack<ReadOnlyAddressBook> addressBookHistory;
    private final Stack<ReadOnlyAddressBook> addressBookUndoHistory;
    private final AddressBook currentAddressBook;

    /**
     * Constructor for a {@code HistoricalAddressBook} object.
     * Initializes while setting current data to the {@code ReadOnlyAddressBook} provided.
     * @param initialAddressBook The first recorded AddressBook
     */
    public HistoricalAddressBook(ReadOnlyAddressBook initialAddressBook) {
        addressBookHistory = new Stack<>();
        addressBookUndoHistory = new Stack<>();
        currentAddressBook = new AddressBook(initialAddressBook);
        addressBookHistory.push(currentAddressBook);
    }

    /**
     * Constructor for a {@code HistoricalAddressBook} object.
     * Initializes with no data.
     */
    public HistoricalAddressBook() {
        addressBookHistory = new Stack<>();
        addressBookUndoHistory = new Stack<>();
        currentAddressBook = new AddressBook();
        addressBookHistory.push(currentAddressBook);
    }

    /**
     * Saves the current {@code AddressBook}
     * Clear the undo history
     */
    public void save() {
        addressBookUndoHistory.clear();
        addressBookHistory.push(new AddressBook(currentAddressBook));
    }

    /**
     * Reverts the most recent changes
     */
    public void undo() {
        if (!canUndo()) {
            throw new RuntimeException("There is no command to undo.");
        }
        addressBookUndoHistory.push(addressBookHistory.pop());
        currentAddressBook.resetData(addressBookHistory.peek());
    }

    /**
     * Reverses the most recent undo action
     */
    public void redo() {
        if (!canRedo()) {
            throw new RuntimeException("There is no command to redo.");
        }
        addressBookHistory.push(addressBookUndoHistory.pop());
        currentAddressBook.resetData(addressBookHistory.peek());
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

    // ======================= Wrapper functions =========================

    //// list overwrite operations

    /**
     * @see AddressBook#setPersons(List)
     */
    public void setPersons(List<Person> persons) {
        currentAddressBook.setPersons(persons);
    }

    /**
     * @see AddressBook#resetData(ReadOnlyAddressBook)
     */
    public void resetData(ReadOnlyAddressBook newData) {
        currentAddressBook.resetData(newData);
        save();
    }

    //// person-level operations

    /**
     * @see AddressBook#hasPerson(Person)
     */
    public boolean hasPerson(Person person) {
        return currentAddressBook.hasPerson(person);
    }

    /**
     * @see AddressBook#addPerson(Person)
     */
    public void addPerson(Person p) {
        currentAddressBook.addPerson(p);
        save();
    }

    /**
     * @see AddressBook#setPerson(Person, Person)
     */
    public void setPerson(Person target, Person editedPerson) {
        currentAddressBook.setPerson(target, editedPerson);
        save();
    }

    /**
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
}
