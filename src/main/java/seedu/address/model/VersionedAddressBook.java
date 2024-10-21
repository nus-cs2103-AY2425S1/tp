package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

public class VersionedAddressBook extends AddressBook {
    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);
        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    public void undo() {
        if (canUndo()) {
            currentStatePointer--;
            resetData(addressBookStateList.get(currentStatePointer));
        }
    }

    public void redo() {
        if (canRedo()) {
            currentStatePointer++;
            resetData(addressBookStateList.get(currentStatePointer));
        }
    }

    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    public boolean canRedo() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }
}