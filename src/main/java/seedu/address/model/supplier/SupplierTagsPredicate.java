package seedu.address.model.supplier;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Supplier}'s {@code Tags} matches any of the tags given.
 */
public class SupplierTagsPredicate implements Predicate<Supplier> {
    private final List<String> tags;

    public SupplierTagsPredicate(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Supplier supplier) {
        return supplier.getTags().stream()
                .anyMatch(tag -> tags.contains(tag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SupplierTagsPredicate
                && tags.equals(((SupplierTagsPredicate) other).tags));
    }
}
