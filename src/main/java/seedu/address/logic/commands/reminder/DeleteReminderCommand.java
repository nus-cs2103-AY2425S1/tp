package seedu.address.logic.commands.reminder;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class DeleteReminderCommand extends Command {
    public static final String COMMAND_WORD = "deleteReminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the reminder identified by the index number used in the displayed reminder list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder: %1$s";

    public static final String MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX = "The reminder index provided is invalid";

    public DeleteReminderCommand(int targetIndex) {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
