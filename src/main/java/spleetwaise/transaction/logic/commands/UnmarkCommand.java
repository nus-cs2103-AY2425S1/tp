package spleetwaise.transaction.logic.commands;

import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Status;

/**
 * Unmarks a transaction identified using its displayed index from the transaction book.
 */
public class UnmarkCommand extends UpdateStatusCommand {

    public static final String COMMAND_WORD = "markUndone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the transaction as undone identified by the index number used in the displayed transaction "
            + "list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TRANSACTION_SUCCESS = "Marked Transaction as Undone: %1$s";

    public UnmarkCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    protected Status getUpdatedStatus() {
        return new Status(false); // Marked as undone
    }

    @Override
    protected String getSuccessMessage() {
        return MESSAGE_UNMARK_TRANSACTION_SUCCESS;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnmarkCommand otherUnmarkCommand)) {
            return false;
        }

        return targetIndex.equals(otherUnmarkCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

