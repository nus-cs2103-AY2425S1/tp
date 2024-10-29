package seedu.address.logic.commands;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Cancels the deletion of a person from the address book.
 */
public class DeleteNCommand extends Command {

    public static final String COMMAND_WORD = "n";

    public static final String MESSAGE_CANCEL_DELETE = "Deletion operation cancelled.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Clear the personToDelete from StaticContext
        StaticContext.setPersonToDelete(null);
        return new CommandResult(MESSAGE_CANCEL_DELETE);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof DeleteNCommand);
    }
}
