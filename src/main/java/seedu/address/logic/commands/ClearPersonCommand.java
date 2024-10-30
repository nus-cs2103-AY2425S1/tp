package seedu.address.logic.commands;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearPersonCommand extends ClearCommand {
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    protected void clearEntity(Model model) {
        model.setAddressBook(new AddressBook());
    }

    /**
     * Returns success message to display upon adding entity.
     */
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }

    @Override
    public boolean equals(Object other) {
        boolean isSameInstance = other == this;
        boolean isClearPersonCommand = other instanceof ClearPersonCommand;

        return isSameInstance || isClearPersonCommand;
    }
}
