package seedu.address.model.person.keywordspredicate;

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
    public abstract boolean equals(Object other);

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

    public String getDisplayString() {
        return String.join(", ", keywords);
    }
}
