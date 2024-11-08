package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Interest} matches the specified keyword.
 */
public class InterestContainsKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

    /**
     * Constructs an {@code InterestContainsKeywordsPredicate} with the specified keyword.
     *
     * @param keyword A keyword to match against a person's interests.
     */
    public InterestContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword.toLowerCase(); // Normalize to lower case for case-insensitive matching
    }

    @Override
    public boolean test(Person person) {
        // Check if the person's interests contain the specified keyword
        return person.getInterests().stream()
                .anyMatch(interest -> interest.toString().toLowerCase().contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof InterestContainsKeywordsPredicate)) {
            return false;
        }
        InterestContainsKeywordsPredicate otherPredicate = (InterestContainsKeywordsPredicate) other;
        return keyword.equals(otherPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keyword", keyword)
                .toString();
    }
}

