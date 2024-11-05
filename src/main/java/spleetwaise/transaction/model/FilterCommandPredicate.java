package spleetwaise.transaction.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.function.Predicate;

import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * Tests that a {@code Transaction} matches any of the condition given.
 */
public class FilterCommandPredicate implements Predicate<Transaction> {

    private final ArrayList<Predicate<Transaction>> filterSubPredicates;


    /**
     * Constructs a {@code FilterCommandPredicate} with the given list of sub-predicates.
     *
     * @param filterSubPredicates The list of sub-predicates to filter the transaction by.
     *                            Must contain at least 1 sub-predicate.
     */
    public FilterCommandPredicate(ArrayList<Predicate<Transaction>> filterSubPredicates) {
        requireNonNull(filterSubPredicates);
        if (filterSubPredicates.isEmpty()) {
            throw new NullPointerException();
        }

        this.filterSubPredicates = filterSubPredicates;
    }

    @Override
    public boolean test(Transaction txn) {
        return filterSubPredicates.stream().reduce(pred -> true, Predicate::and).test(txn);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommandPredicate otherFilterCommandPredicate)) {
            return false;
        }

        return filterSubPredicates.containsAll(otherFilterCommandPredicate.filterSubPredicates)
                && otherFilterCommandPredicate.filterSubPredicates.containsAll(filterSubPredicates);
    }

    @Override
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this);
        for (int i = 0; i < filterSubPredicates.size(); i++) {
            sb.add("pred" + i, filterSubPredicates.get(i));
        }
        return sb.toString();
    }
}
