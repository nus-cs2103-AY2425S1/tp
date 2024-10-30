package seedu.address.model.person.predicates;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code StudyGroupTag}s matches any of the keywords given.
 */
public class StudyGroupsContainKeywordsPredicate implements Predicate<Person> {
    private final Set<String> keywords;

    /**
     * Constructs a {@code StudyGroupsContainKeywordsPredicate}.
     *
     * @param keywords A set of valid keywords.
     */
    public StudyGroupsContainKeywordsPredicate(Set<String> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getStudyGroupTags().stream()
                        .anyMatch(
                                studyGroup -> StringUtil.containsWordIgnoreCase(studyGroup.studyGroupName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudyGroupsContainKeywordsPredicate)) {
            return false;
        }

        StudyGroupsContainKeywordsPredicate otherStudyGroupsContainKeywordsPredicate =
                (StudyGroupsContainKeywordsPredicate) other;
        return keywords.equals(otherStudyGroupsContainKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
