package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Deletes an appointment identified using it's displayed index from the address book.
 */
public class DeleteAppointmentCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + ParserUtil.APPOINTMENT_ENTITY_STRING
            + ": Deletes the appointment identified by the index number used in the appointment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + ParserUtil.APPOINTMENT_ENTITY_STRING + " " + "1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted Appointment: %1$s";

    public DeleteAppointmentCommand(Index targetIndex) {
        super(targetIndex);
    }

    /**
     * Retrieves the list of filtered appointments from the model.
     *
     * @param model The model containing the filtered list.
     * @return A list of filtered appointments.
     */
    @Override
    protected List<Appointment> getFilteredList(Model model) {
        return model.getFilteredAppointmentList();
    }

    /**
     * Deletes the specified appointment entity from the model.
     *
     * @param model The model to delete the appointment from.
     * @param entity The appointment entity to be deleted.
     * @throws NullPointerException if the specified entity is null.
     * @throws AssertionError if the entity is not an instance of Appointment.
     */
    @Override
    protected void deleteEntity(Model model, Object entity) {
        requireNonNull(entity);

        assert entity instanceof Appointment;

        model.deleteAppointment((Appointment) entity);
    }

    /**
     * Returns the success message to display after a successful appointment deletion.
     *
     * @return A string indicating the success of the deletion operation.
     */
    @Override
    protected String getSuccessMessage() {
        return MESSAGE_DELETE_APPOINTMENT_SUCCESS;
    }

    /**
     * Returns the message to display when an invalid appointment index is provided.
     *
     * @return A string indicating that the appointment index is invalid.
     */
    @Override
    protected String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX;
    }

    /**
     * Formats the given appointment entity into a string representation.
     *
     * @param entity The entity to format.
     * @return A string representation of the given appointment entity.
     * @throws AssertionError if the entity is not an instance of Appointment.
     */
    @Override
    protected String formatEntity(Object entity) {
        assert entity instanceof Appointment;

        return Messages.formatAppointment((Appointment) entity);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAppointmentCommand otherDeleteAppointmentCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteAppointmentCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
