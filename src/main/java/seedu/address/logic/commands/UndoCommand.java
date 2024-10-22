package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Undoes the last deletion.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last deletion.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        return DeleteCommand.undo(model);
    }
}
