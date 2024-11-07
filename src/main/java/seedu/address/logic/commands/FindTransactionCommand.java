package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.TransactionContainsKeywordsPredicate;

/**
 * Finds and lists all transactions for any person whose description contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindTransactionCommand extends Command {
    public static final String COMMAND_WORD = "findt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all transactions in the current view whose descriptions contain any of "
            + "the keywords. (case-insensitive)\n"
            + "ONLY use this command when you are viewing the transaction list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " food transport";
    private final TransactionContainsKeywordsPredicate predicate;

    /**
     * Creates a FindTransactionCommand to find the transactions
     * that meet the specified {@code TransactionContainsKeywordsPredicate}
     */
    public FindTransactionCommand(TransactionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.getIsViewTransactions()) {
            throw new CommandException(String.format(Messages.MESSAGE_MUST_BE_TRANSACTION_LIST, COMMAND_WORD));
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        Person targetPerson = lastShownList.get(0);
        model.updateTransactionListPredicate(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size(), Messages.format(targetPerson)));
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
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
