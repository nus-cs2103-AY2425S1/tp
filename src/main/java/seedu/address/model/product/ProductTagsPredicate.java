package seedu.address.model.product;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Product}'s {@code Tags} matches any of the tags given.
 */
public class ProductTagsPredicate implements Predicate<Product> {
    private final List<String> tags;

    public ProductTagsPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Product product) {
        return product.getTags().stream()
                .anyMatch(tag -> tags.contains(tag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ProductTagsPredicate
                && tags.equals(((ProductTagsPredicate) other).tags));
    }
}
