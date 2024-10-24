package seedu.address.model.person.predicates;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

import java.util.List;

/**
 * Tests that a {@code Person}'s {@code Product} matches any of the keywords given.
 */
public class ProductContainsKeywordPredicate extends SupplierPredicate {

    public ProductContainsKeywordPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Person person) {
        // Partial String matching unlike the normal AB3
        return person.getProducts().stream()
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
        return new ToStringBuilder(this).add("keyword: ", keyword).toString();
    }
}
