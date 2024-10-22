package seedu.address.model.person;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 * Matching is case-insensitive and matches full words only.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final Set<String> keywords;

    /**
     * Constructs a {@code NameContainsKeywordsPredicate} with the given keywords.
     *
     * @param keywords A list of keywords to match.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        // Convert all keywords to lower case and store in a Set for faster lookup
        this.keywords = new HashSet<>();
        for (String keyword : keywords) {
            this.keywords.add(keyword.toLowerCase());
        }
    }

    @Override
    public boolean test(Person person) {

        // Split person's name into words
        String[] nameWords = person.getName().fullName.toLowerCase().split("\\s+");

        // Check if any keyword matches any of the name words
        for (String word : nameWords) {
            if (keywords.contains(word)) {
                return true;
            }
        }
        return false;
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
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
