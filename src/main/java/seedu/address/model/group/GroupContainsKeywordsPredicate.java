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

    /**
     * Constructs a {@code GroupContainsKeywordsPredicate} with the specified list of keywords.
     *
     * <p>This constructor ensures that the list of keywords is not {@code null}, throwing an
     * {@code AssertionError} if {@code keywords} is {@code null}. The keywords will be used
     * to filter or match against the group names or other criteria in the predicate.
     *
     * @param keywords A {@code List<String>} of keywords to be used in the predicate.
     * @throws AssertionError If {@code keywords} is {@code null}.
     */
    public GroupContainsKeywordsPredicate(List<String> keywords) {
        assert keywords != null : "Keywords cannot be null";
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        try {
            assert person != null : "Person cannot be null";
            GroupList groupList = person.getGroupList();

            if (groupList == null) {
                return false;
            }

            if (!hasOnlyValidGroups(groupList)) {
                throw new ParseException(INVALID_GROUP);
            }

            if (hasPrefixMatch(keywords, groupList)) {
                return true;
            }

            return hasWordInList(keywords, groupList);

        } catch (ParseException pe) {
            logger.warning(pe.getMessage());
            return false;
        }
    }

    /**
     * Checks if the Group List only has valid groups
     * @param groupList
     * @return boolean indicating if all the groups in the list are valid
     */
    public boolean hasOnlyValidGroups(GroupList groupList) {
        Set<Group> invalidGroups = groupList.getUnmodifiableGroups().stream()
                .filter(group -> !Group.isValidGroupName(group.getGroupName())).collect(Collectors.toSet());

        return invalidGroups.isEmpty();
    }

    /**
     * Checks if any of the given keywords match the start of any group name in the provided group list.
     * This matching is case-insensitive.
     *
     * @param keywords A list of keywords to check against the group names.
     * @param groupList The GroupList containing the groups to check.
     * @return {@code true} if any group name starts with any of the keywords, otherwise {@code false}.
     */
    public boolean hasPrefixMatch(List<String> keywords, GroupList groupList) {
        return keywords.stream().anyMatch(keyword -> groupList.getUnmodifiableGroups().stream()
                .anyMatch(group -> group.getGroupName().toLowerCase().startsWith(keyword.toLowerCase())));
    }

    /**
     * Checks if any of the given keywords appear as a word within any of the group names in the provided group list.
     * This matching is case-insensitive.
     *
     * @param keywords A list of keywords to check against the group names.
     * @param groupList The GroupList containing the groups to check.
     * @return {@code true} if any of the keywords are found as a complete word in any group name (case-insensitive),
     *         otherwise {@code false}.
     */
    public boolean hasWordInList(List<String> keywords, GroupList groupList) {
        return groupList.getUnmodifiableGroups().stream().anyMatch(group -> keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getGroupName(), keyword)));
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
