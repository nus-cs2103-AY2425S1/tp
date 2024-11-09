package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MONTH;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Transaction;
import seedu.address.model.person.TransactionDatePredicate;

/**
 * Summarizes the transactions whose dates range between the first day of start month and the last day of end month.
 * Shows the transactions whose dates are in the specified range.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Summarizes the transactions in the current list"
            + "whose dates range between the first day of start month and the last day of end month.\n"
            + "Parameters: " + PREFIX_START_MONTH + "START_MONTH " + PREFIX_END_MONTH + "END_MONTH\n"
            + "START_MONTH and END_MONTH must follow the yyyy-mm format. "
            + "START_MONTH must be before or equal to END_MONTH.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_START_MONTH + "2024-09 " + PREFIX_END_MONTH + "2024-12";

    public static final String MESSAGE_SUCCESS = "The total amount of transactions from %s to %s is: %s";
    public static final String MESSAGE_NO_TRANSACTIONS_FOUND = "No transactions found from %s to %s";

    private final TransactionDatePredicate predicate;

    /**
     * Creates a SummaryCommand to summarize the transactions in the range between start month and end month inclusive.
     *
     * @param predicate the predicate containing the start and end month.
     */
    public SummaryCommand(TransactionDatePredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.getIsViewTransactions()) {
            throw new CommandException(String.format(Messages.MESSAGE_MUST_BE_TRANSACTION_LIST, COMMAND_WORD));
        }
        model.updateTransactionListPredicate(predicate);
        if (model.getFilteredTransactionList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_TRANSACTIONS_FOUND, predicate.getFormattedStartDate(),
                    predicate.getFormattedEndDate()));
        }
        double summary = model.getFilteredTransactionList().stream().mapToDouble(Transaction::getAmount).sum();
        String sumString;
        if (summary < 0) {
            sumString = String.format("-$%.2f", -summary);
        } else {
            sumString = String.format("$%.2f", summary);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, predicate.getFormattedStartDate(),
                predicate.getFormattedEndDate(), sumString));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SummaryCommand)) {
            return false;
        }
        SummaryCommand otherCommand = (SummaryCommand) other;
        return this.predicate.equals(otherCommand.predicate);
    }
}
