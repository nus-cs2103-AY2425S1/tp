package spleetwaise.transaction.logic.commands;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import spleetwaise.address.commons.core.LogsCenter;
import spleetwaise.address.commons.core.index.Index;
import spleetwaise.address.commons.util.ToStringBuilder;
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

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        logger.log(Level.INFO, "Executing DeleteCommand with index: {0}", targetIndex.getZeroBased());

        CommonModel model = CommonModel.getInstance();

        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.WARNING, "Invalid transaction index: {0}", targetIndex.getZeroBased());
            throw new CommandException("The transaction index provided is invalid");
        }

        Transaction transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTransaction(transactionToDelete);
        logger.log(Level.INFO, "Deleted transaction: {0}", transactionToDelete);
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
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
