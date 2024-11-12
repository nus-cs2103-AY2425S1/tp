package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Cancels the pending command.
 */
public class CancelPendingCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Pending command has been cancelled.";
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, false, false,
                CommandTabChange.NONE, CommandDetailChange.NONE);
    }
}
