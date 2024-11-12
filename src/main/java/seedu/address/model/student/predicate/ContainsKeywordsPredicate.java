package seedu.address.model.student.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Student;

/**
 * Tests that a {@code Student} matches any of the keywords given.
 */
public abstract class ContainsKeywordsPredicate implements Predicate<Student> {
    protected final List<String> keywords;

    public ContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
