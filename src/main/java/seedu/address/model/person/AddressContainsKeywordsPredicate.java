package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a AddressContainsKeywordsPredicate with the given wedding
     *
     * @param keywords The keywords to check against
     */
    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests if a given person has address containing some keywords.
     * Returns true only if some keywords are found in the person's address
     *
     * @param person The person to test
     * @return true if some keywords are found in the person's address, false otherwise
     */
    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(person.getAddress().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddressContainsKeywordsPredicate
                && keywords.equals(((AddressContainsKeywordsPredicate) other).keywords));
    }
}
