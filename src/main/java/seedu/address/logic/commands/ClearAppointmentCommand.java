package seedu.address.logic.commands;

import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;

/**
 * Clears the appointment book.
 */
public class ClearAppointmentCommand extends ClearCommand {
    public static final String MESSAGE_SUCCESS = "Appointment has been cleared!";

    /**
     * Clears the appointment book in the model.
     *
     * @param model {@code Model} which the command should operate on.
     */
    protected void clearEntity(Model model) {
        model.setAppointmentBook(new AppointmentBook());
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
        boolean isClearAppointmentCommand = other instanceof ClearAppointmentCommand;

        return isSameInstance || isClearAppointmentCommand;
    }
}
