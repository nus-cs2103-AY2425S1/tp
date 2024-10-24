package seedu.address.model.group;

import static seedu.address.model.UserPrefs.MATCH_RATIO;

import java.util.List;
import java.util.function.Predicate;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Group}'s {@code GroupName} matches any of the keywords given.
 */
public class GroupNameContainsKeywordsPredicate implements Predicate<Group> {
    private final List<String> keywords;

    public GroupNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Group group) {
        return keywords.stream()
            .anyMatch(keyword ->
                FuzzySearch.tokenSetPartialRatio(group.getGroupName().getGroupName().toLowerCase(),
                    keyword.toLowerCase()) > MATCH_RATIO);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupNameContainsKeywordsPredicate)) {
            return false;
        }

        GroupNameContainsKeywordsPredicate otherPredicate = (GroupNameContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("keywords", keywords)
            .toString();
    }
}
