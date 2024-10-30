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

    @Override
    protected List<Appointment> getFilteredList(Model model) {
        return model.getFilteredAppointmentList();
    }

    @Override
    protected void deleteEntity(Model model, Object entity) {
        requireNonNull(entity);

        assert entity instanceof Appointment;

        model.deleteAppointment((Appointment) entity);
    }

    @Override
    protected String getSuccessMessage() {
        return MESSAGE_DELETE_APPOINTMENT_SUCCESS;
    }

    @Override
    protected String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX;
    }

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
        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        DeleteAppointmentCommand otherDeleteAppointmentCommand = (DeleteAppointmentCommand) other;
        return targetIndex.equals(otherDeleteAppointmentCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
