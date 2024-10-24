package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Resets the attendance of all students.
 */
public class ResetAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "resetAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets the attendance of all students.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Attendance reset successfully.";
    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        requireNonNull(model);
        model.resetAttendance();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof MarkAttendanceCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
