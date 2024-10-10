package seedu.address.model.group;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */

public class GroupContainsKeywordsPredicate implements Predicate<Group> {

        private final List<String> keywords;

        public GroupContainsKeywordsPredicate(List<String> keywords)  {
            this.keywords = keywords;
        }

        @Override
        public boolean test(Group group) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getGroupName().groupName, keyword));
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof seedu.address.model.group.GroupContainsKeywordsPredicate)) {
                return false;
            }

            seedu.address.model.group.GroupContainsKeywordsPredicate otherGroupContainsKeywordsPredicate = (seedu.address.model.group.GroupContainsKeywordsPredicate) other;
            return keywords.equals(otherGroupContainsKeywordsPredicate.keywords);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).add("keywords", keywords).toString();
        }

        public List<String> getKeywords() {
            return this.keywords;
        }
}

