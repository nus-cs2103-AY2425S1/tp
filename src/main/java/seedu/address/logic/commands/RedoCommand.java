package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Redoes the latest undo by the user, if any.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Redoes the latest undo by the user, if any.\n"
        + "Example: redo";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("This is the redo command");
    }
}
