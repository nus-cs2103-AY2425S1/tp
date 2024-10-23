package seedu.address.logic.commands;

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
        // TODO: Implement logic to show all appointments
        return new CommandResult("TODO: Implement schedule_all logic");
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
