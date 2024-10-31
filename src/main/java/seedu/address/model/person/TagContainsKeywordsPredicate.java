package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final String weddingKeywords;

    /**
     * Constructs a TagContainsKeywordsPredicate with the given tag keywords.
     */
    public TagContainsKeywordsPredicate(String weddingKeywords) {
        this.weddingKeywords = weddingKeywords;
    }

    @Override
    public boolean test(Person person) {
        List<String> tagNames = person.getTags().stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());

        boolean matchesWedding = tagNames.stream()
                                    .anyMatch(tagName -> {
                                        return tagName.toLowerCase().equals(weddingKeywords);
                                    });

        return matchesWedding;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.person.TagContainsKeywordsPredicate)) {
            return false;
        }

        seedu.address.model.person.TagContainsKeywordsPredicate otherPredicate =
                (seedu.address.model.person.TagContainsKeywordsPredicate) other;
        return weddingKeywords.equals(otherPredicate.weddingKeywords);
    }

    @Override
    public String toString() {
        return "TagContainsKeywordsPredicate{"
                + "weddingKeywords=" + weddingKeywords
                + '}';
    }
}

