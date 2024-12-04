package seedu.address.model.product;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Products}'s {@code Name} matches any of the keywords given.
 */
public class ProductNameContainsKeywordsPredicate implements Predicate<Product> {
    private final List<String> keywords;

    public ProductNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Product product) {
        return keywords.stream()
                .anyMatch(keyword -> product.getName().getNormalizedName().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProductNameContainsKeywordsPredicate)) {
            return false;
        }

        ProductNameContainsKeywordsPredicate otherNamePredicate = (ProductNameContainsKeywordsPredicate) other;
        return keywords.equals(otherNamePredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}

