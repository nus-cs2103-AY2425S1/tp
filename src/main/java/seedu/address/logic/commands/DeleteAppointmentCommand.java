package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Deletes an appointment using the unique ID.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "delete-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment identified by the unique ID.\n"
            + "Parameters: UNIQUE_ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS =
            "Deleted appointment for %1$s with Dr. %2$s at %3$s %4$s.";
    public static final String MESSAGE_INVALID_APPOINTMENT_ID = "Invalid Unique ID, appointment does not exist.";

    private final Appointment appointmentToDelete;

    /**
     * Constructs a {@code DeleteAppointmentCommand} with the appointment to delete.
     *
     * @param appointmentToDelete The appointment object that should be deleted.
     */
    public DeleteAppointmentCommand(Appointment appointmentToDelete) {
        requireNonNull(appointmentToDelete);
        this.appointmentToDelete = appointmentToDelete;
    }

    /**
     * Executes the command to delete the specified appointment.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} message indicating the success or failure of the operation.
     * @throws CommandException If the specified appointment ID does not exist.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean appointmentDeleted = Appointment.deleteAppointmentById(appointmentToDelete.getId());
        if (!appointmentDeleted) {
            throw new CommandException(MESSAGE_INVALID_APPOINTMENT_ID);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete.getPatient().getName().toString(),
                appointmentToDelete.getDoctor().getName().toString(),
                appointmentToDelete.getDate().toString(),
                appointmentToDelete.getTime().toString()));
    }

    /**
     * Checks if this {@code DeleteAppointmentCommand} is equal to another object.
     * Two {@code DeleteAppointmentCommand} instances are considered equal if they are the same instance
     * or if they both have the same {@code appointmentToDelete}.
     *
     * @param other The other object to compare to.
     * @return true if both are the same instance or have the same {@code appointmentToDelete}, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }
        DeleteAppointmentCommand otherCommand = (DeleteAppointmentCommand) other;
        return Objects.equals(appointmentToDelete, otherCommand.appointmentToDelete);
    }
}
