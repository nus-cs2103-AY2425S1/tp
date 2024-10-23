package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the previous command and restores the address book to its previous state
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the previous command"
            + " and returns EduConnect to the previous original state";

    public static final String MESSAGE_SUCCESS = "Undid the previous command!";

    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        model.undoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected boolean requiresCommit() {
        return false;
    }
}
