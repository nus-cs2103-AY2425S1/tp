package seedu.address.model.wedding;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Wedding}'s couple names match all of the keywords given.
 * Different from NameContainsKeywordsPredicate as it requires all keywords to be present in the names.
 * Example: Given keywords ["John", "Mary"], a wedding with name "John Mary Smith" would match
 * because both "John" and "Mary" are present in the name.
 */
public class NameMatchesWeddingPredicate implements Predicate<Wedding> {
    private final List<String> keywords;

    /**
     * Constructs a NameMatchesWeddingPredicate with the given list of names.
     * Any multi-word names in the list will be split into individual words.
     * For example, "John Mary" would be split into ["John", "Mary"].
     *
     * @param names A list of strings containing names or name keywords to match against
     */
    public NameMatchesWeddingPredicate(List<String> names) {
        // Split any multi-word strings into individual words
        this.keywords = names.stream()
                .flatMap(name -> Arrays.stream(name.split("\\s+")))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Tests if a given wedding's name contains all the keywords.
     * The test is case-insensitive.
     * Returns true only if all keywords are found in the wedding name.
     * If no keywords were provided (empty list), returns false.
     *
     * @param wedding The wedding to test
     * @return true if the wedding's name contains all keywords, false otherwise
     */
    @Override
    public boolean test(Wedding wedding) {
        return !keywords.isEmpty() && keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(wedding.getName().fullName, keyword));
    }

    /**
     * Compares this predicate with another object for equality.
     * Two NameMatchesWeddingPredicates are equal if they have the same keywords.
     *
     * @param other The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameMatchesWeddingPredicate)) {
            return false;
        }

        NameMatchesWeddingPredicate otherNameMatchesWeddingPredicate = (NameMatchesWeddingPredicate) other;
        return keywords.equals(otherNameMatchesWeddingPredicate.keywords);
    }

    /**
     * Returns a string representation of this NameMatchesWeddingPredicate.
     * The string includes the list of keywords being matched against.
     *
     * @return A string representation of this predicate
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
