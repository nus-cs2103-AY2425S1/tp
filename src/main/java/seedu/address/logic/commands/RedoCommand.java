package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redoes the latest undo by the user, if any.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Successfully redone the latest undone change to contact data!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Redoes the latest undo by the user, if any.\n"
        + "Example: redo";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.redoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
