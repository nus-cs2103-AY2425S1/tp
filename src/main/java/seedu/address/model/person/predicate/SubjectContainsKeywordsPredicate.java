package seedu.address.model.person.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Subject} matches any of the keywords given.
 */
public class SubjectContainsKeywordsPredicate extends ContainsKeywordsPredicate {
    public SubjectContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getSubjects().stream().anyMatch(
                        subject -> StringUtil.containsWordIgnoreCase(
                                subject.subjectName, keyword)));
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
}
