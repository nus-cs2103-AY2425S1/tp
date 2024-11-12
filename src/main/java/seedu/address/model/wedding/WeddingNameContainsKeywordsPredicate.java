package seedu.address.model.wedding;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Wedding}'s {@code WeddingName} matches any of the keywords given.
 */
public class WeddingNameContainsKeywordsPredicate implements Predicate<Wedding> {
    private final List<String> keywords;

    public WeddingNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Wedding wedding) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(wedding.getWeddingName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WeddingNameContainsKeywordsPredicate)) {
            return false;
        }

        WeddingNameContainsKeywordsPredicate otherNameContainsKeywordsPredicate =
                (WeddingNameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
