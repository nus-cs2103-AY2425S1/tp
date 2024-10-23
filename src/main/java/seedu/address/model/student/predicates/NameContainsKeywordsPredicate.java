package seedu.address.model.student.predicates;

import java.util.Collection;
import java.util.Objects;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Student;

/**
 * Tests that a {@code Student}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate extends AttributeContainsKeywordsPredicate<String> {

    public NameContainsKeywordsPredicate(Collection<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Student student) {
        return getKeywords().stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(student.getName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return Objects.equals(getKeywords(), otherNameContainsKeywordsPredicate.getKeywords());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", getKeywords()).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKeywords());
    }
}
