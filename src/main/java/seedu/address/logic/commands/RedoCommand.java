package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the previous command and restores the address book to its previous state
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the previously undone command"
            + " and restores EduConnect to its updated state";

    public static final String MESSAGE_SUCCESS = "Redid the previous command!";

    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        model.redoAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected boolean requiresCommit() {
        return false;
    }
}
