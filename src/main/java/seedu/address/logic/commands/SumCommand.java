package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.DateTimeUtil.DEFAULT_DATE_FORMATTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MONTH;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Transaction;

/**
 * Summarizes the transactions whose dates range between the first day of start month and the last day of end month.
 * Shows the transactions whose dates are in the specified range.
 */
public class SumCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Summarizes the transactions whose dates "
            + "range between the first day of start month and the last day of end month.\n"
            + "Parameters: " + PREFIX_START_MONTH + "START_MONTH " + PREFIX_END_MONTH + "END_MONTH\n"
            + "START_MONTH and END_MONTH must follow the yyyy-mm format.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_START_MONTH + "2024-09 " + PREFIX_END_MONTH + "2024-12";

    public static final String MESSAGE_SUCCESS = "The total amount of transactions from %s to %s is: $%.2f";
    private final YearMonth startMonth;
    private final YearMonth endMonth;

    /**
     * Creates a SumCommand to summarize the transactions in the range between start month and end month inclusive.
     *
     * @param start the start month.
     * @param end the end month.
     */
    public SumCommand(YearMonth start, YearMonth end) {
        this.startMonth = start;
        this.endMonth = end;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        LocalDate startDate = startMonth.atDay(1);
        LocalDate endDate = endMonth.atEndOfMonth();

        double summary = model.getFilteredTransactionList().stream()
                .filter(transaction -> (
                        transaction.getDate().isEqual(startDate) || transaction.getDate().isAfter(startDate))
                        && (transaction.getDate().isEqual(endDate) || transaction.getDate().isBefore(endDate)))
                .mapToDouble(Transaction::getAmount)
                .sum();
        List<Transaction> listToShow = model.getFilteredTransactionList().stream()
                .filter(transaction -> (
                        transaction.getDate().isEqual(startDate) || transaction.getDate().isAfter(startDate))
                        && (transaction.getDate().isEqual(endDate) || transaction.getDate().isBefore(endDate)))
                .collect(Collectors.toList());
        model.updateTransactionList(listToShow);
        return new CommandResult(String.format(MESSAGE_SUCCESS, startDate.format(DEFAULT_DATE_FORMATTER),
                endDate.format(DEFAULT_DATE_FORMATTER), summary));
    }
}
