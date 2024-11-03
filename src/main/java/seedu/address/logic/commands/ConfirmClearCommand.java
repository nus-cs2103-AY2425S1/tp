package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Requests confirmation before clear operation.
 */
public class ConfirmClearCommand extends Command {
    public static final String CLEAR_CONFIRMATION_MESSAGE = "Are you sure you want to clear your data? (Y/N)";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(CLEAR_CONFIRMATION_MESSAGE, false, false,
                CommandTabChange.NONE, CommandDetailChange.NONE);
    }
}
