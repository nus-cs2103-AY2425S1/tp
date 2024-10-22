package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Address} contains any of the given postal code keywords.
 */
public class PostalContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PostalContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword ->
                person.getPostalCode().toString().contains(keyword));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PostalContainsKeywordsPredicate)) {
            return false;
        }

        PostalContainsKeywordsPredicate otherPredicate = (PostalContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }
}
