package seedu.address.model.group;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

//@@author gho7sie

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
            .anyMatch(keyword -> {
                GroupName groupName = group.getGroupName();
                String groupNameString = groupName.getGroupName().toLowerCase();
                return groupNameString.contains(keyword.toLowerCase());
            });
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
