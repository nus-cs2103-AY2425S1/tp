package seedu.address.model.restaurant;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;



/**
 * Tests that a {@code Restaurant}'s {@code Name} matches any of the keywords given.
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

        String filterBy = keywords.get(0);

        return keywords.stream()
                    .anyMatch(keyword -> restaurant.getPriceTags().stream()
                           .anyMatch(tag -> {
                               String cleanTag = tag.toString().replaceAll("[\\[\\]]", "");
                               return StringUtil.containsWordIgnoreCase(cleanTag, keyword);
                           }));
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
