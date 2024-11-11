package spleetwaise.transaction.model.filterpredicate;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Tests if a {@code Transaction}'s {@code Status} matches the given {@code Status}.
 */
public class StatusFilterPredicate implements Predicate<Transaction> {

    private final Status statusToFilter;

    /**
     * Creates a {@code StatusFilterPredicate} that tests if a {@code Transaction}'s {@code Status} matches the
     * given {@code Status}.
     *
     * @param statusToFilter The {@code Status} to filter by.
     */
    public StatusFilterPredicate(Status statusToFilter) {
        requireNonNull(statusToFilter);
        this.statusToFilter = statusToFilter;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getStatus().equals(statusToFilter);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusFilterPredicate otherStatusFilterPredicate)) {
            return false;
        }

        return statusToFilter.equals(otherStatusFilterPredicate.statusToFilter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("status", statusToFilter)
                .toString();
    }
}
