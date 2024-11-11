package spleetwaise.transaction.logic.commands;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import spleetwaise.commons.core.LogsCenter;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.transaction.logic.Messages;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Abstract command to update the status of a transaction.
 */
public abstract class UpdateStatusCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(MarkCommand.class);

    protected final Index targetIndex;

    public UpdateStatusCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Subclasses must provide the new status.
     */
    protected abstract Status getUpdatedStatus();

    /**
     * Subclasses must provide the success message.
     */
    protected abstract String getSuccessMessage();

    @Override
    public CommandResult execute() throws CommandException {
        logger.log(
                Level.INFO, "Executing {0} with index: {1}",
                new Object[]{ this.getClass().getSimpleName(), targetIndex.getZeroBased() }
        );

        CommonModelManager model = CommonModelManager.getInstance();

        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.WARNING, "Invalid transaction index: {0}", targetIndex.getZeroBased());
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction txnToUpdate = lastShownList.get(targetIndex.getZeroBased());
        Transaction updatedTxn = txnToUpdate.setStatus(getUpdatedStatus());

        model.setTransaction(txnToUpdate, updatedTxn);
        model.updateFilteredTransactionList();

        logger.log(Level.INFO, "Updated transaction: {0}", updatedTxn);
        return new CommandResult(String.format(getSuccessMessage(), updatedTxn));
    }
}
