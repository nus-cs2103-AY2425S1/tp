package seedu.address.logic.commands;

import seedu.address.model.AddressBook;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearPersonCommand extends ClearCommand {
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    protected void clearEntity(Model model) {
        model.setAppointmentBook(new AppointmentBook());
        model.setAddressBook(new AddressBook());
    }

    /**
     * Returns success message to display upon adding entity.
     *
     * @return success message
     */
    protected String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }

    @Override
    public boolean equals(Object otherCommand) {
        return (otherCommand instanceof ClearPersonCommand);
    }
}
