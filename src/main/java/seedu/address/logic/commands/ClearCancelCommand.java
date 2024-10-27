package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Cancels a pending clear command.
 */
public class ClearCancelCommand extends Command {
    public static final String MESSAGE_SUCCESS = "Clear operation cancelled.";
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, false, false,
                CommandTabChange.EVENT, CommandDetailChange.SIMPLIFIED);
    }
}
