package seedu.address.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

/**
 * Represents the different states of the Address Book for the user.
 */
public class VersionedAddressBook extends AddressBook {
    public static final String MESSAGE_NO_MORE_HISTORY = "This is the earliest version that user can retrieve";
    public static final String MESSAGE_UNSAVED_CHANGES = "There are unsaved changes in the current state. "
            + "Please commit or discard the changes before undoing.";
    private final ArrayList<AddressBook> addressBookStateList;
    private int currentStatePointer;
    private AddressBook current;


    /**
     * Constructs an empty state list for the address book.
     */
    public VersionedAddressBook() {
        addressBookStateList = new ArrayList<>();
        currentStatePointer = -1;
        current = new AddressBook();
        this.commitAddressBook();
    }

    /**
     * Constructs a state list for the address book and saves the initial copy.
     */
    public VersionedAddressBook(ReadOnlyAddressBook addressBook) {
        addressBookStateList = new ArrayList<>();
        currentStatePointer = -1;
        current = new AddressBook(addressBook);
        this.commitAddressBook();
    }

    /**
     * Saves the whole address book state in the history.
     */
    public void commitAddressBook() {
        currentStatePointer++;
        addressBookStateList.subList(currentStatePointer, addressBookStateList.size()).clear();
        addressBookStateList.add(currentStatePointer, new AddressBook(current));
    }

    /**
     * Reverts the given AddressBook to the previous state.
     */
    public void undoAddressBook() throws CommandException {
        if (currentStatePointer <= 0) {
            throw new CommandException(MESSAGE_NO_MORE_HISTORY);
        }

        if (!current.equals(addressBookStateList.get(currentStatePointer))) {
            throw new CommandException(MESSAGE_UNSAVED_CHANGES);
        }

        currentStatePointer--;
        current = new AddressBook(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Discards the unsaved changes in the current state.
     */
    public void discardUnsavedChanges() {
        current = new AddressBook(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Return an unmodifiable list containing copies of the AddressBook instances for testing purpose.
     */
    public ArrayList<AddressBook> getAddressBookStateList() {
        ArrayList<AddressBook> copy = new ArrayList<>();
        for (AddressBook addressBook : addressBookStateList) {
            copy.add(new AddressBook(addressBook)); // Assuming AddressBook is immutable
        }
        return copy;
    }

    @Override
    public void setPersons(List<Person> persons) {
        getCurrentAddressBook().setPersons(persons);
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        getCurrentAddressBook().resetData(newData);
    }

    @Override
    public boolean hasPerson(Person person) {
        return getCurrentAddressBook().hasPerson(person);
    }

    @Override
    public boolean hasPhone(Person person) {
        return getCurrentAddressBook().hasPhone(person);
    }

    @Override
    public boolean hasEmail(Person person) {
        return getCurrentAddressBook().hasEmail(person);
    }

    @Override
    public void addPerson(Person p) {
        getCurrentAddressBook().addPerson(p);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        getCurrentAddressBook().setPerson(target, editedPerson);
    }

    @Override
    public void removePerson(Person key) {
        getCurrentAddressBook().removePerson(key);
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return getCurrentAddressBook().getPersonList();
    }

    @Override
    public String toString() {
        return getCurrentAddressBook().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof VersionedAddressBook)) {
            return false;
        }
        VersionedAddressBook other = (VersionedAddressBook) o;

        // Compare addressBookStateList and currentStatePointer
        return currentStatePointer == other.currentStatePointer
                && addressBookStateList.equals(other.addressBookStateList)
                && current.equals(other.current);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressBookStateList, currentStatePointer, current);
    }

    /**
     * Returns the current version of {@code AddressBook}.
     */
    public AddressBook getCurrentAddressBook() {
        return this.current;
    }
}
