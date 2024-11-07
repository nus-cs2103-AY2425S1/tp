package spleetwaise.transaction.model.filterpredicate;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Category;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Tests if a {@code Transaction}'s {@code Description} contains the given {@code Description}.
 */
public class CategoryFilterPredicate implements Predicate<Transaction> {

    private final Category categoryToFilter;

    /**
     * Creates a {@code CategoryFilterPredicate} that tests if a {@code Transaction}'s {@code Category} contains
     * the given {@code Category}.
     *
     * @param categoryToFilter The {@code Category} to filter by.
     */
    public CategoryFilterPredicate(Category categoryToFilter) {
        requireNonNull(categoryToFilter);
        this.categoryToFilter = categoryToFilter;
    }

    /**
     * Tests if a {@code Transaction}'s {@code Category} contains the given {@code Category}.
     *
     * @param transaction The {@code Transaction} to test.
     * @return {@code true} if the {@code Transaction}'s {@code Category} contains the given {@code Category}.
     */
    @Override
    public boolean test(Transaction transaction) {
        return transaction.containsCategory(categoryToFilter);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CategoryFilterPredicate otherCategoryFilterPredicate)) {
            return false;
        }

        return categoryToFilter.equals(otherCategoryFilterPredicate.categoryToFilter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("category", categoryToFilter)
                .toString();
    }
}
