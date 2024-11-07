package seedu.address.model.supplier.predicates;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.supplier.Supplier;

/**
 * Tests that a {@code Supplier}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordPredicate extends SupplierPredicate {

    /**
     * Constructs a {@code NameContainsKeywordPredicate}.
     *
     * @param keywords Keyword to test against.
     */
    public NameContainsKeywordPredicate(String keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Supplier supplier) {
        // Partial String matching unlike the normal AB3
        return supplier.getName().toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordPredicate)) {
            return false;
        }

        NameContainsKeywordPredicate otherNameContainsPredicate = (NameContainsKeywordPredicate) other;
        return super.equals(otherNameContainsPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
