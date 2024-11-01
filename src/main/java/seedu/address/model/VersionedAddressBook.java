package seedu.address.model;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents the different states of the Address Book for the user.
 */
public class VersionedAddressBook extends AddressBook {
    public static final String MESSAGE_CONSTRAINTS = "This is the earliest version that user can retrieve";
    private final ArrayList<AddressBook> addressBookStateList;
    private int currentStatePointer;
    private AddressBook current;


    /**
     * Constructs a state list for the address book and saves the initial copy.
     */
    public VersionedAddressBook(ReadOnlyAddressBook addressBook) {
        super(addressBook);
        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(addressBook));
        currentStatePointer = 0;
        current = new AddressBook(addressBook);
    }

    /**
     * Saves the whole address book state in the history.
     */
    public void commitAddressBook() {
        currentStatePointer++;
        addressBookStateList.add(currentStatePointer, new AddressBook(current));
        current = new AddressBook(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Reverts the given AddressBook to the previous state.
     */
    public void undoAddressBook() throws CommandException {
        if (currentStatePointer == 0) {
            throw new CommandException(MESSAGE_CONSTRAINTS);
        }
        currentStatePointer--;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        VersionedAddressBook other = (VersionedAddressBook) o;

        // Compare addressBookStateList and currentStatePointer
        return currentStatePointer == other.currentStatePointer
                && addressBookStateList.equals(other.addressBookStateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressBookStateList);
    }

    /**
     * Returns the current version of {@code AddressBook}.
     */
    public AddressBook getCurrentAddressBook() {
        return this.current;
    }
}
