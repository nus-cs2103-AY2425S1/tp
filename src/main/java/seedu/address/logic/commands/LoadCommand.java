package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
/**
 * Load command that will set address book to the saved file.
 */
public class LoadCommand extends Command {
    public static final String COMMAND_WORD = "load";
    public static final String MESSAGE_SUCCESS = "The saved address book has been loaded!";
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true);
    }

    @Override
    public void undo(Model model) {

    }
}
