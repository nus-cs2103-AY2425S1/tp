package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

public class ConfirmationCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Are you sure you want to clear all data? (Y/N)";
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, false, false,
                CommandTabChange.EVENT, CommandDetailChange.SIMPLIFIED);
    }
}
