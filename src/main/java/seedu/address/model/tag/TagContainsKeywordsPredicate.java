package seedu.address.model.tag;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests whether a {@code Person}'s tags contain any of the specified keywords.
 * Keyword matching is case-insensitive.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .map(tag -> tag.tagName)
                .anyMatch(tagName -> keywords.stream()
                        .anyMatch(keyword -> tagName.toLowerCase().contains(keyword.toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }
}

