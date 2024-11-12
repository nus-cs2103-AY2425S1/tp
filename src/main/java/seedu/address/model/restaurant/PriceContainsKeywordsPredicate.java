package seedu.address.model.restaurant;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Restaurant}'s {@code Name} matches any of the keywords
 * given.
 */
public class PriceContainsKeywordsPredicate implements Predicate<Restaurant> {

    private final List<String> keywords;

    public PriceContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Restaurant restaurant) {
        if (keywords == null || keywords.isEmpty()) {
            return false;
        }

        return keywords.stream()
                .anyMatch(keyword -> {
                    Price price = restaurant.getPrice();
                    return price.isRepresentedBy(keyword);
                });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PriceContainsKeywordsPredicate)) {
            return false;
        }

        PriceContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (PriceContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("price labels", keywords).toString();
    }
}
