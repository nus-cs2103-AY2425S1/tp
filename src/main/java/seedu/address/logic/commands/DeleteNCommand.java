package seedu.address.logic.commands;

import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Cancels the deletion of a person from the address book or a wedding from the wedding.
 */
public class DeleteNCommand extends Command {

    public static final String COMMAND_WORD = "n";

    public static final String MESSAGE_CANCEL_DELETE = "Deletion operation cancelled.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Clear the personToDelete from StaticContext
        StaticContext.setPersonToDelete(null);
        // Clear the weddingToDelete from StaticContext
        StaticContext.setWeddingToDelete(null);
        // Clear the clearAddressBookPending flag from StaticContext
        StaticContext.setClearAddressBookPending(false);
        // Clear the clearWeddingBookPending flag from StaticContext
        StaticContext.setClearWeddingBookPending(false);
        return new CommandResult(MESSAGE_CANCEL_DELETE);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof DeleteNCommand);
    }
}
