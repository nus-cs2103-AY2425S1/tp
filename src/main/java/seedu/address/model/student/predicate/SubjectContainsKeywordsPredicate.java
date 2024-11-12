package seedu.address.model.student.predicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.student.Student;

/**
 * Tests that a {@code Student}'s {@code Subject} matches any of the keywords given.
 */
public class SubjectContainsKeywordsPredicate extends ContainsKeywordsPredicate {
    public SubjectContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword -> student.getSubjects().stream().anyMatch(
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
