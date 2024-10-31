package seedu.address.model.history;

import java.util.ArrayList;

import seedu.address.model.AddressBook;

public class VersionedAddressBook {
    private ArrayList<AddressBook> addressBookStateList;
    private int currentStatePointer;

    public VersionedAddressBook() {
        addressBookStateList = new ArrayList<>();
        currentStatePointer = 0;
    }

    /**
     * Saves the whole address book state in the history.
     */
    public void commitAddressBook(AddressBook addressBook) {
        addressBookStateList.add(currentStatePointer, addressBook);
        currentStatePointer++;
    }

    /**
     * Reverses the AddressBook to the previous state.
     */
    public void undoAddressBook(AddressBook addressBook) {
        assert currentStatePointer > 0 : "No command that change the AddressBook has been executed.";
        AddressBook previousState = addressBookStateList.get(currentStatePointer--);
        addressBook.resetData(previousState);
    }
}
