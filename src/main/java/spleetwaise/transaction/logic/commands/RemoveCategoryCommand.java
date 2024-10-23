package spleetwaise.transaction.logic.commands;

import static java.util.Objects.requireNonNull;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_TXN;

import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * This class represents a command for removing categories to transactions.
 */
public class RemoveCategoryCommand extends Command {

    /**
     * The word used to trigger this command in the input.
     */
    public static final String COMMAND_WORD = "removeTxnCat";

    public static final String MESSAGE_SUCCESS = "Category removed from transaction: [%s] with [%s]";

    public static final String MESSAGE_MISSING_CATEGORY = "No such category found in the specified transaction.";

    /**
     * The message usage string that explains how to use this command.
     */
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Remove a category from transaction.\n" + "Parameters: " + PREFIX_TXN
                    + "TRANSACTION_INDEX " + PREFIX_CATEGORY + "CATEGORY " + "Example: " + COMMAND_WORD + " "
                    + PREFIX_TXN + "1 " + PREFIX_CATEGORY + "Transport";

    private final Transaction transaction;
    private final Category category;

    /**
     * Creates an RemoveCategoryCommand to remove tag to specified {@code Transaction}.
     *
     * @param transaction The transaction where the category is removed.
     * @param category The category to be removed
     */
    public RemoveCategoryCommand(Transaction transaction, Category category) {
        requireNonNull(transaction);
        requireNonNull(category);

        this.transaction = transaction;
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!transaction.containsCategory(category)) {
            throw new CommandException(MESSAGE_MISSING_CATEGORY);
        }

        transaction.removeCategory(category);

        return new CommandResult(String.format(MESSAGE_SUCCESS, transaction.getId(), category));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemoveCategoryCommand otherCommand)) {
            return false;
        }

        return transaction.equals(otherCommand.transaction) && category.equals(otherCommand.category);
    }
}
