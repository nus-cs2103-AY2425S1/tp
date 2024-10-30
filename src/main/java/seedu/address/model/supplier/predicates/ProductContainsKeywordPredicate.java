package seedu.address.model.supplier.predicates;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.supplier.Supplier;

/**
 * Tests that a {@code Supplier}'s {@code Product} matches any of the keywords given.
 */
public class ProductContainsKeywordPredicate extends SupplierPredicate {

    public ProductContainsKeywordPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Supplier supplier) {
        // Partial String matching unlike the normal AB3
        return supplier.getProducts().stream()
                        .anyMatch(product -> product.toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProductContainsKeywordPredicate)) {
            return false;
        }

        ProductContainsKeywordPredicate otherProductContainsKeywordPredicate = (ProductContainsKeywordPredicate) other;
        return super.equals(otherProductContainsKeywordPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
