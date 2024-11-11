package spleetwaise.transaction.model.filterpredicate;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Description;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Tests if a {@code Transaction}'s {@code Description} contains the given {@code Description}.
 */
public class DescriptionFilterPredicate implements Predicate<Transaction> {

    private final Description descriptionToFilter;

    /**
     * Creates a {@code DescriptionFilterPredicate} that tests if a {@code Transaction}'s {@code Description} contains
     * the given {@code Description}.
     *
     * @param descriptionToFilter The {@code Description} to filter by.
     */
    public DescriptionFilterPredicate(Description descriptionToFilter) {
        requireNonNull(descriptionToFilter);
        this.descriptionToFilter = descriptionToFilter;
    }

    /**
     * Tests if a {@code Transaction}'s {@code Description} contains the given {@code Description}.
     *
     * @param transaction The {@code Transaction} to test.
     * @return {@code true} if the {@code Transaction}'s {@code Description} contains the given {@code Description}.
     */
    @Override
    public boolean test(Transaction transaction) {
        return transaction.getDescription().toString().toLowerCase()
                .contains(descriptionToFilter.toString().toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DescriptionFilterPredicate otherDescriptionFilterPredicate)) {
            return false;
        }

        return descriptionToFilter.equals(otherDescriptionFilterPredicate.descriptionToFilter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("description", descriptionToFilter)
                .toString();
    }
}
