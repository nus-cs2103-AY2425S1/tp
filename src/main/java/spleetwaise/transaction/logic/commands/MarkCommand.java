package spleetwaise.transaction.logic.commands;

import spleetwaise.address.commons.core.index.Index;
import spleetwaise.address.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Status;

/**
 * Marks a transaction identified using its displayed index from the transaction book.
 */
public class MarkCommand extends UpdateStatusCommand {

    public static final String COMMAND_WORD = "markTxn";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the transaction identified by the index number used in the displayed transaction list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TRANSACTION_SUCCESS = "Marked Transaction: %1$s";

    public MarkCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    protected Status getUpdatedStatus() {
        return new Status(true); // Marked as done
    }

    @Override
    protected String getSuccessMessage() {
        return MESSAGE_MARK_TRANSACTION_SUCCESS;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkCommand otherMarkCommand)) {
            return false;
        }

        return targetIndex.equals(otherMarkCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
