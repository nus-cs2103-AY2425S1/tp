package spleetwaise.transaction.logic.commands;

import java.util.List;

import spleetwaise.address.commons.core.index.Index;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Deletes a transaction identified using its displayed index from the transaction book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "deleteTxn";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the transaction identified by the index number used in the displayed transaction list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Transaction: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        CommonModel model = CommonModel.getInstance();

        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("The transaction index provided is invalid.");
        }

        Transaction transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTransaction(transactionToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand otherDeleteCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return "DeleteCommand{targetIndex=" + targetIndex + "}";
    }
}
