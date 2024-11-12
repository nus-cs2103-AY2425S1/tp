package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code University} matches the keyword given.
 */
public class UniversityContainsKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

    public UniversityContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    public String getKeyword() {
        return keyword;
    }
    @Override
    public boolean test(Person person) {
        return person.getUniversity().value.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniversityContainsKeywordsPredicate
                && keyword.equals(((UniversityContainsKeywordsPredicate) other).keyword));
    }
}
