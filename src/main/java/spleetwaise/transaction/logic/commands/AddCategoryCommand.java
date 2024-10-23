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
 * This class represents a command for adding categories to transactions.
 */
public class AddCategoryCommand extends Command {

    /**
     * The word used to trigger this command in the input.
     */
    public static final String COMMAND_WORD = "addTxnCat";

    public static final String MESSAGE_SUCCESS = "Category added to transaction: [%s] with [%s]";

    public static final String MESSAGE_EMPTY_CATEGORY = "Cannot define empty category.";

    /**
     * The message usage string that explains how to use this command.
     */
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Add a category to transaction.\n" + "Parameters: " + PREFIX_TXN + "TRANSACTION_INDEX "
                    + PREFIX_CATEGORY + "CATEGORY " + "Example: " + COMMAND_WORD + " " + PREFIX_TXN + "1 "
                    + PREFIX_CATEGORY + "Transport";

    private final Transaction transaction;
    private final Category category;

    /**
     * Creates an AddCategoryCommand to add tag to specified {@code Transaction}.
     *
     * @param transaction The transaction where the category is added.
     * @param category The category to be added
     */
    public AddCategoryCommand(Transaction transaction, Category category) {
        requireNonNull(transaction);
        requireNonNull(category);

        this.transaction = transaction;
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        transaction.addCategory(category);

        return new CommandResult(String.format(MESSAGE_SUCCESS, transaction.getId(), category));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCategoryCommand otherCommand)) {
            return false;
        }

        return transaction.equals(otherCommand.transaction) && category.equals(otherCommand.category);
    }
}
