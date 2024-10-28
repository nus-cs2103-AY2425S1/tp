package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Requests confirmation before clear operation.
 */
public class ClearConfirmationCommand extends Command {

    public static final String CONFIRMATION_MESSAGE = "Are you sure you want to clear all data? (Y/N)";
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(CONFIRMATION_MESSAGE, false, false,
                CommandTabChange.NONE, CommandDetailChange.NONE);
    }
}
