package seedu.address.model.student.predicates;

import java.util.Collection;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Student;

/**
 * Tests that a {@code Student}'s attribute matches any of the keywords given.
 */
public abstract class AttributeContainsKeywordsPredicate<T> implements Predicate<Student> {
    private final Collection<T> keywords;

    public AttributeContainsKeywordsPredicate(Collection<T> keywords) {
        this.keywords = keywords;
    }

    public Collection<T> getKeywords() {
        return keywords;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AttributeContainsKeywordsPredicate)) {
            return false;
        }

        AttributeContainsKeywordsPredicate<?> otherPredicate = (AttributeContainsKeywordsPredicate<?>) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
