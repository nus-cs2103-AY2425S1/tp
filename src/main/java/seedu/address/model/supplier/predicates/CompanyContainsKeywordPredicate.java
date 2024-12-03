package seedu.address.model.supplier.predicates;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.supplier.Supplier;


/**
 * Tests that a {@code Supplier}'s {@code Company} matches any of the keywords given.
 */
public class CompanyContainsKeywordPredicate extends SupplierPredicate {

    /**
     * Constructs a {@code CompanyContainsKeywordPredicate}.
     *
     * @param keyword Keyword to test against.
     */
    public CompanyContainsKeywordPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Supplier supplier) {
        requireNonNull(supplier);
        // Partial String matching unlike the normal AB3
        return supplier.getCompany().toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompanyContainsKeywordPredicate)) {
            return false;
        }

        CompanyContainsKeywordPredicate otherCompanyNameContainsPredicate =
                (CompanyContainsKeywordPredicate) other;

        return super.equals(otherCompanyNameContainsPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
