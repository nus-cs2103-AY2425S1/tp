package seedu.address.model.owner;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Owner}'s {@code Name} matches any of the keywords given.
 */
public class OwnerNameContainsKeywordsPredicate implements Predicate<Owner> {
    private final List<String> keywords;

    public OwnerNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Owner owner) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsTextIgnoreCase(owner.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof OwnerNameContainsKeywordsPredicate)) {
            return false;
        }

        OwnerNameContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (OwnerNameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
