package seedu.address.logic.commands.clientcommands.appointmentcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandUtils;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * Deletes a client's appointment identified by the client's name.
 * The name must correspond to a person in the current list of persons.
 */
public class DeleteAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "deleteapt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Successfully deleted appointment from %1$s";

    private final Index targetIndex;

    /**
     * Creates a {@code DeleteAppointmentCommand} to delete the appointment of the person with the specified
     * {@code Name}.
     *
     * @param targetIndex The index of the person whose appointment will be deleted.
     */
    public DeleteAppointmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command to delete the appointment of the person identified by the {@code targetIndex}.
     * If the person is found in the filtered list of persons, the appointment is set to an empty {@code Appointment}.
     *
     * @param model The model which contains the list of persons.
     * @return The result of the command execution.
     * @throws CommandException If the person cannot be found in the filtered list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int zeroBased = targetIndex.getZeroBased();
        CommandUtils.handleInvalidPersonIndex(zeroBased, lastShownList.size());

        Person personToDeleteAppointment = lastShownList.get(zeroBased);
        Person personWithoutAppointment = AppointmentCommandsUtil
                .createPersonWithAppointment(personToDeleteAppointment,
                        personToDeleteAppointment.getRole(),
                        Appointment.EMPTY_APPOINTMENT);

        model.setPerson(personToDeleteAppointment, personWithoutAppointment);

        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                personToDeleteAppointment.getName()));
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
                .add("targetIndex", targetIndex.getOneBased())
                .toString();
    }
}
