package seedu.address.model.property;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Property}'s {@code landlordName} range contains the keywords.
 */
public class LandlordNameContainsKeywordsPredicate implements Predicate<Property> {
    private final List<String> keywords;

    /**
     * Landlord name must be present and not null.
     */
    public LandlordNameContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Property property) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(property.getName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LandlordNameContainsKeywordsPredicate)) {
            return false;
        }

        LandlordNameContainsKeywordsPredicate otherLandlordNameContainsKeywordsPredicate =
                (LandlordNameContainsKeywordsPredicate) other;

        return keywords.equals(otherLandlordNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
