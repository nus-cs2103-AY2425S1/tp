package seedu.address.model.wedding;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Wedding}'s couple names match any of the keywords given.
 * Different from NameContainsKeywordsPredicate as it requires all keywords to be present in the names.
 */
public class NameMatchesWeddingPredicate implements Predicate<Wedding> {
    private final List<String> keywords;

    public NameMatchesWeddingPredicate(List<String> names) {
        this.keywords = names;
    }

    @Override
    public boolean test(Wedding wedding) {
        return !keywords.isEmpty() && keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(wedding.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameMatchesWeddingPredicate)) {
            return false;
        }

        NameMatchesWeddingPredicate otherNameMatchesWeddingPredicate = (NameMatchesWeddingPredicate) other;
        return keywords.equals(otherNameMatchesWeddingPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
