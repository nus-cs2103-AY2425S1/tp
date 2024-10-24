package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Interest} matches any of the keywords given.
 */
public class InterestContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public InterestContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        // Check if any of the person's interests match any of the keywords
        return keywords.stream()
                .anyMatch(keyword ->
                        person.getInterests().stream()
                                .anyMatch(interest -> interest.toString().toLowerCase().contains(keyword.toLowerCase()))
                );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InterestContainsKeywordsPredicate)) {
            return false;
        }

        InterestContainsKeywordsPredicate otherPredicate = (InterestContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

