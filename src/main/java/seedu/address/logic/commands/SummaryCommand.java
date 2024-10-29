package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateTimeUtil.DEFAULT_DATE_FORMATTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MONTH;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Transaction;

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

    public static final String MESSAGE_SUCCESS = "The total amount of transactions from %s to %s is: $%.2f";
    private final YearMonth startMonth;
    private final YearMonth endMonth;

    /**
     * Creates a SummaryCommand to summarize the transactions in the range between start month and end month inclusive.
     *
     * @param start the start month.
     * @param end the end month.
     */
    public SummaryCommand(YearMonth start, YearMonth end) {
        this.startMonth = start;
        this.endMonth = end;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.getIsViewTransactions()) {
            throw new CommandException(String.format(Messages.MESSAGE_MUST_BE_TRANSACTION_LIST, COMMAND_WORD));
        }

        LocalDate startDate = startMonth.atDay(1);
        LocalDate endDate = endMonth.atEndOfMonth();

        List<Transaction> transactionsInRange = model.getFilteredTransactionList().stream()
                .filter(transaction -> (
                        transaction.getDate().isEqual(startDate) || transaction.getDate().isAfter(startDate))
                        && (transaction.getDate().isEqual(endDate) || transaction.getDate().isBefore(endDate)))
                .collect(Collectors.toList());
        model.updateTransactionList(transactionsInRange);
        double summary = transactionsInRange.stream().mapToDouble(Transaction::getAmount).sum();
        return new CommandResult(String.format(MESSAGE_SUCCESS, startDate.format(DEFAULT_DATE_FORMATTER),
                endDate.format(DEFAULT_DATE_FORMATTER), summary));
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
        return this.startMonth.equals(otherCommand.startMonth) && this.endMonth.equals(otherCommand.endMonth);
    }
}
