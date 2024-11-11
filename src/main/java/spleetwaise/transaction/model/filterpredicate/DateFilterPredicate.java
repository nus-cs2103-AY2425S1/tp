package spleetwaise.transaction.model.filterpredicate;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Date;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Tests if a {@code Transaction}'s {@code Date} matches the given {@code Date}.
 */
public class DateFilterPredicate implements Predicate<Transaction> {

    private final Date dateToFilter;

    /**
     * Creates a {@code DateFilterPredicate} that tests if a {@code Transaction}'s {@code Date} matches the
     * given {@code Date}.
     *
     * @param dateToFilter The {@code Date} to filter by.
     */
    public DateFilterPredicate(Date dateToFilter) {
        requireNonNull(dateToFilter);
        this.dateToFilter = dateToFilter;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getDate().equals(dateToFilter);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateFilterPredicate otherDateFilterPredicate)) {
            return false;
        }

        return dateToFilter.equals(otherDateFilterPredicate.dateToFilter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", dateToFilter)
                .toString();
    }
}
