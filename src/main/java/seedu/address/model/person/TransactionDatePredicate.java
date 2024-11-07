package seedu.address.model.person;

import static seedu.address.commons.util.DateTimeUtil.DEFAULT_DATE_FORMATTER;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.function.Predicate;

/**
 * Tests that a {@code Transaction}'s {@code date} is within the range between start and end month.
 */
public class TransactionDatePredicate implements Predicate<Transaction> {
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Creates a TransactionDatePredicate.
     *
     * @param start the start month.
     * @param end the end month.
     */
    public TransactionDatePredicate(YearMonth start, YearMonth end) {
        this.startDate = start.atDay(1);
        this.endDate = end.atEndOfMonth();
    }
    @Override
    public boolean test(Transaction transaction) {
        return transaction.getDate().isEqual(startDate)
                || transaction.getDate().isEqual(endDate)
                || (transaction.getDate().isAfter(startDate) && transaction.getDate().isBefore(endDate));
    }

    public String getFormattedStartDate() {
        return startDate.format(DEFAULT_DATE_FORMATTER);
    }

    public String getFormattedEndDate() {
        return endDate.format(DEFAULT_DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionDatePredicate)) {
            return false;
        }

        TransactionDatePredicate otherTransactionDatePredicate = (TransactionDatePredicate) other;
        return startDate.equals(otherTransactionDatePredicate.startDate)
                && endDate.equals(otherTransactionDatePredicate.endDate);
    }
}
