package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Subject(s)} matches any of the keywords given.
 */
public class SubjectContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public SubjectContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getSubjects().stream()
                        .anyMatch(subject -> StringUtil.containsWordIgnoreCase(subject.subjectName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SubjectContainsKeywordsPredicate)) {
            return false;
        }

        SubjectContainsKeywordsPredicate otherSubjectContainsKeywordsPredicate =
                (SubjectContainsKeywordsPredicate) other;
        return keywords.equals(otherSubjectContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
