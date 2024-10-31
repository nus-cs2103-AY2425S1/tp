package seedu.address.model.history;

import java.util.ArrayList;
import java.util.Objects;

import seedu.address.model.AddressBook;

/**
 * Represents the different states of the Address Book for the user.
 */
public class VersionedAddressBook {
    private ArrayList<AddressBook> addressBookStateList;
    private int currentStatePointer;

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
     * Reverses the AddressBook to the previous state.
     */
    public void undoAddressBook(AddressBook addressBook) {
        assert currentStatePointer >= 0 : "This is the earliest version that user can retrieve";
        currentStatePointer--;
        AddressBook previousState = addressBookStateList.get(currentStatePointer);
        addressBook.resetData(previousState);
    }

    /**
     * Returns the address book state list for testing purpose.
     */
    public ArrayList<AddressBook> getAddressBookStateList() {
        return addressBookStateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        VersionedAddressBook other = (VersionedAddressBook) o;
        return this.addressBookStateList.equals(other.getAddressBookStateList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressBookStateList);
    }
}
