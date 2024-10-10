package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code University} matches the keyword given.
 */
public class UniversityContainsKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

    public UniversityContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsWordIgnoreCase(person.getUniversity().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniversityContainsKeywordsPredicate
                && keyword.equals(((UniversityContainsKeywordsPredicate) other).keyword));
    }
}
