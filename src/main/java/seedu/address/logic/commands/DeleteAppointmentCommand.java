package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Deletes an appointment identified using its displayed index from the appointment list.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "deletea";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment identified by the index number used in the displayed appointment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Deleted appointment:\n%s";

    private final Index targetIndex;

    public DeleteAppointmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        SortedList<Appointment> lastShownList = (SortedList<Appointment>) model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        int sourceIndex = lastShownList.getSourceIndex(targetIndex.getZeroBased());
        Appointment appointment = model.deleteAppointment(sourceIndex);
        return new CommandResult(String.format(
                MESSAGE_DELETE_APPOINTMENT_SUCCESS, appointment), true, false, false);
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
