package seedu.address.model.client;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Transaction}'s {@code description} matches any of the keywords given.
 */
public class TransactionContainsKeywordsPredicate implements Predicate<Transaction> {
    private final List<String> keywords;

    public TransactionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Transaction transaction) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(transaction.getDescription(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TransactionContainsKeywordsPredicate)) {
            return false;
        }

        TransactionContainsKeywordsPredicate otherTransactionContainsKeywordsPredicate =
                (TransactionContainsKeywordsPredicate) other;
        return keywords.equals(otherTransactionContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
