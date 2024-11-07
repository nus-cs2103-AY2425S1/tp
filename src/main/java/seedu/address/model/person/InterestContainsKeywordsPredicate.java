package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Interest} matches the given OR keywords or AND groups.
 */
public class InterestContainsKeywordsPredicate implements Predicate<Person> {
    private final List<List<String>> andKeywords;
    private final List<String> orKeywords;
    /**
     * Constructs an {@code InterestContainsKeywordsPredicate} with specified AND and OR keyword lists.
     *
     * @param andKeywords A list of lists, where each inner list represents a group of keywords that must all be
     *                    matched (AND condition). Each person must contain all keywords in at least one inner list
     *                    to satisfy this condition.
     * @param orKeywords A list of keywords where any single keyword can match (OR condition). A person satisfies
     *                   this condition if they contain at least one of the keywords in this list.
     */
    public InterestContainsKeywordsPredicate(List<List<String>> andKeywords, List<String> orKeywords) {
        this.andKeywords = andKeywords;
        this.orKeywords = orKeywords;
    }

    @Override
    public boolean test(Person person) {
        // If both keyword lists are empty, return false (no matches)
        if (andKeywords.isEmpty() && orKeywords.isEmpty()) {
            return false;
        }

        // Check the OR keywords: at least one keyword in orKeywords must match
        boolean orMatch = orKeywords.isEmpty() || orKeywords.stream()
                .anyMatch(keyword ->
                        person.getInterests().stream()
                                .anyMatch(interest -> interest.toString().toLowerCase().contains(keyword.toLowerCase()))
                );

        // Check the AND keywords: every group in andKeywords must have all keywords in the group matching
        boolean andMatch = andKeywords.stream()
                .allMatch(group ->
                        group.stream()
                                .allMatch(keyword ->
                                        person.getInterests().stream()
                                                .anyMatch(interest ->
                                                        interest.toString().toLowerCase()
                                                                .contains(keyword.toLowerCase()))
                                )
                );

        // Both OR and AND conditions must be satisfied
        return orMatch && andMatch;
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
        return andKeywords.equals(otherPredicate.andKeywords)
                && orKeywords.equals(otherPredicate.orKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("andKeywords", andKeywords)
                .add("orKeywords", orKeywords)
                .toString();
    }
}
