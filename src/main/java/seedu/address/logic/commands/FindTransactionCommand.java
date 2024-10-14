package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.TransactionContainsKeywordsPredicate;

/**
 * Finds and lists all transactions for any person whose description contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindTransactionCommand extends Command {
    public static final String COMMAND_WORD = "findt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all transactions whose descriptions contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " food transport";

    private final TransactionContainsKeywordsPredicate predicate;

    public FindTransactionCommand(TransactionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTransactionCommand)) {
            return false;
        }

        FindTransactionCommand otherFindTransactionCommand = (FindTransactionCommand) other;
        return predicate.equals(otherFindTransactionCommand.predicate);
    }
}
