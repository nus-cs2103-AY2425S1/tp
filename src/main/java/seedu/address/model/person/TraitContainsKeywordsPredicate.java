package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s trait (eg: Tag, Name) matches any of the keywords given.
 */
public abstract class TraitContainsKeywordsPredicate<T> implements Predicate<T> {
    protected final List<String> keywords;
    public TraitContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public abstract boolean test(T t);

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TraitContainsKeywordsPredicate<?> otherNameContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

    public String getDisplayString() {
        return String.join(", ", keywords);
    }
}
