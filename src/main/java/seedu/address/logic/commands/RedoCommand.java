package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the previous command and restores the address book to its previous state
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    // TODO FILL IN
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": TODO FILL IN";

    // TODO FILL IN
    public static final String MESSAGE_SUCCESS = "!";

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
