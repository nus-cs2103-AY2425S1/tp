package spleetwaise.transaction.model.filterpredicate;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Tests if a {@code Transaction}'s {@code Amount} matches the given {@code Amount}.
 */
public class AmountFilterPredicate implements Predicate<Transaction> {

    private final Amount amountToFilter;

    /**
     * Creates a {@code AmountFilterPredicate} that tests if a {@code Transaction}'s {@code Amount} matches the
     * given {@code Amount}.
     *
     * @param amountToFilter The {@code Amount} to filter by.
     */
    public AmountFilterPredicate(Amount amountToFilter) {
        requireNonNull(amountToFilter);
        this.amountToFilter = amountToFilter;
    }

    @Override
    public boolean test(Transaction transaction) {
        return transaction.getAmount().equals(amountToFilter);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AmountFilterPredicate otherAmountFilterPredicate)) {
            return false;
        }

        return amountToFilter.equals(otherAmountFilterPredicate.amountToFilter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("amount", amountToFilter)
                .toString();
    }
}
