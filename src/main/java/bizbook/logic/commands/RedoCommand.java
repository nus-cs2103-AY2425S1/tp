package bizbook.logic.commands;

import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;

/**
 * Reverts the changes made by the undo function.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redoes the previous undo command that was executed.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_REDO_SUCCESS = "Changes has been reverted!";
    public static final String MESSAGE_REDO_FAILURE = "Unable revert changes because "
            + "there is no newer version to revert to!";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.canRedo()) {
            throw new CommandException(MESSAGE_REDO_FAILURE);
        }

        model.setFocusPerson(null);
        model.redoAddressBookVersion();
        return new CommandResult(MESSAGE_REDO_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RedoCommand)) {
            return false;
        }
        return true;
    }
}
