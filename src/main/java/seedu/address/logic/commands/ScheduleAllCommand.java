package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays all appointments for all dates.
 */
public class ScheduleAllCommand extends Command {

    public static final String COMMAND_WORD = "schedule_all";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all appointments for all days.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Displayed all appointments";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleAllCommand); // instanceof handles nulls
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
