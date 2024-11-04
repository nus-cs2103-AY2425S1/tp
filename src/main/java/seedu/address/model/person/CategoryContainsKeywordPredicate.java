package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Category} matches any of the keyword given.
 */
public class CategoryContainsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    /**
     * @param keyword the category to track
     */
    public CategoryContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        // Check if category is null before trying to compare
        if (person.getCategoryDisplayName() == null) {
            return false; // If the category is null, it does not match the keyword
        }
        return StringUtil.containsWordIgnoreCase(person.getCategoryDisplayName(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CategoryContainsKeywordPredicate otherNameContainsKeywordsPredicate)) {
            return false;
        }

        return keyword.equals(otherNameContainsKeywordsPredicate.keyword);
    }

    @Override
    public String toString() {
        return this.keyword;
    }
}
