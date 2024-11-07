package seedu.address.model.group;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.list.GroupList;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Group} matches any of the keywords given.
 * Matching is case-insensitive and matches full words only.
 */
public class GroupContainsKeywordsPredicate implements Predicate<Person> {
    public static final String INVALID_GROUP = "Group found is in an invalid format";
    private static final Logger logger = LogsCenter.getLogger(GroupContainsKeywordsPredicate.class);
    private final List<String> keywords;

    public GroupContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        try {
            GroupList groupList = person.getGroupList();
            if (groupList == null) {
                return false;
            }

            Set<Group> invalidGroups = groupList.getUnmodifiableGroups().stream()
                    .filter(group -> !Group.isValidGroupName(group.getGroupName())).collect(Collectors.toSet());

            // This should be empty
            if (!invalidGroups.isEmpty()) {
                throw new ParseException(INVALID_GROUP);
            }

            boolean prefixMatch = keywords.stream()
                    .anyMatch(keyword -> groupList.getUnmodifiableGroups().stream()
                            .anyMatch(group -> group.getGroupName().startsWith(keyword)));

            if (prefixMatch) {
                return true;
            }

            return groupList.getUnmodifiableGroups().stream()
                    .anyMatch(group -> keywords.stream()
                            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getGroupName(), keyword)));

        } catch (ParseException pe) {
            logger.warning(pe.getMessage());
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupContainsKeywordsPredicate)) {
            return false;
        }

        GroupContainsKeywordsPredicate otherGroupContainsKeywordsPredicate = (GroupContainsKeywordsPredicate) other;
        return keywords.equals(otherGroupContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
