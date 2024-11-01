package seedu.address.model.history;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;

/**
 * Represents the different states of the Address Book for the user.
 */
public class VersionedAddressBook {
    private final ArrayList<AddressBook> addressBookStateList;
    private int currentStatePointer;

    public static final String MESSAGE_CONSTRAINTS = "This is the earliest version that user can retrieve";

    /**
     * Constructs a state list for the address book and saves the initial copy.
     */
    public VersionedAddressBook(AddressBook addressBook) {
        addressBookStateList = new ArrayList<>();
        currentStatePointer = 0;
        addressBookStateList.add(new AddressBook(addressBook));
    }

    /**
     * Saves the whole address book state in the history.
     */
    public void commitAddressBook(AddressBook addressBook) {
        currentStatePointer++;
        addressBookStateList.add(currentStatePointer, new AddressBook(addressBook));
    }

    /**
     * Reverts the given AddressBook to the previous state.
     */
    public void undoAddressBook(AddressBook addressBook) throws CommandException {
        if (!(currentStatePointer >= 0)) {
            throw new CommandException(MESSAGE_CONSTRAINTS);
        }
        currentStatePointer--;
        AddressBook previousState = addressBookStateList.get(currentStatePointer);
        addressBook.resetData(previousState);
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
        return this.addressBookStateList.equals(other.getAddressBookStateList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressBookStateList);
    }
}
