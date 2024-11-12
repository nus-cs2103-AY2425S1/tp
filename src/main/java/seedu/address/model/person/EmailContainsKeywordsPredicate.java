package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a EmailContainsKeywordsPredicate with the given wedding
     *
     * @param keywords The keywords to check against
     */
    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests if a given person has name containing some keywords.
     * Returns true only if some keywords are found in the person's email
     *
     * @param person The person to test
     * @return true if some keywords are found in the person's email, false otherwise
     */
    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(person.getEmail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }
}
