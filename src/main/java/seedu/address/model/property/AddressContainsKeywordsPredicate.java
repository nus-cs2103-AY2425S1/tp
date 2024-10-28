package seedu.address.model.property;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Property}'s {code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Property> {
    private final List<String> keywords;

    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Property property) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(property.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddressContainsKeywordsPredicate)) {
            return false;
        }

        AddressContainsKeywordsPredicate otherPredicate = (AddressContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
