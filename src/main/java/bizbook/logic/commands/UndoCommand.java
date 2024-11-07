package bizbook.logic.commands;

import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;

/**
 * Reverts the changes made to the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the previous command that was executed.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Changes has been reverted!";
    public static final String MESSAGE_UNDO_FAILURE = "Unable revert changes because "
            + "there is no older version to revert to!";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.canUndo()) {
            throw new CommandException(MESSAGE_UNDO_FAILURE);
        }

        model.setFocusPerson(null);
        model.revertAddressBookVersion();
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UndoCommand)) {
            return false;
        }
        return true;
    }
}
