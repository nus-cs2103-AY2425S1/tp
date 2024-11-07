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

    public static final String MESSAGE_NO_PENDING_OPERATION = "No pending delete operation.";


    /**
     * Executes the DeleteNCommand.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes the result of executing the command.
     * @throws CommandException if the deletion operation is not pending.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (StaticContext.hasNoDeleteOperation()) {
            return new CommandResult(MESSAGE_NO_PENDING_OPERATION);
        }
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
