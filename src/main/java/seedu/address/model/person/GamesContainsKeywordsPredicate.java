package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Games} matches any of the keywords given.
 */
public class GamesContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public GamesContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getGames().keySet().stream()
                        .anyMatch(gameName -> StringUtil.containsWordIgnoreCase(gameName, keyword))
                );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GamesContainsKeywordsPredicate)) {
            return false;
        }

        GamesContainsKeywordsPredicate otherGamesContainsKeywordsPredicate = (GamesContainsKeywordsPredicate) other;
        return keywords.equals(otherGamesContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
