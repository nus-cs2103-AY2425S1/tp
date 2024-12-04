package seedu.address.model.listing;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Listing}'s {@code Name} matches any of the keywords given.
 */
public class ListingContainsKeywordsPredicate implements Predicate<Listing> {
    private final List<String> keywords;

    public ListingContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Listing listing) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(listing.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListingContainsKeywordsPredicate otherPredicate)) {
            return false;
        }

        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
