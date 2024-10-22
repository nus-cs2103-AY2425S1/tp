package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} or {@code Nric} matches any of the keywords given.
 */
public class NameNricContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameNricContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getNric().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameNricContainsKeywordsPredicate)) {
            return false;
        }

        NameNricContainsKeywordsPredicate otherNameNricContainsKeywordsPredicate =
                (NameNricContainsKeywordsPredicate) other;
        return keywords.equals(otherNameNricContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
