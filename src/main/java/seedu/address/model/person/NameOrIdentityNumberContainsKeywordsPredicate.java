package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameOrIdentityNumberContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameOrIdentityNumberContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword)
                        || StringUtil.containsWordIgnoreCase(person.getIdentityNumber().identificationNumber, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameOrIdentityNumberContainsKeywordsPredicate)) {
            return false;
        }

        NameOrIdentityNumberContainsKeywordsPredicate otherNameOrIdentityNumberContainsKeywordsPredicate =
                (NameOrIdentityNumberContainsKeywordsPredicate) other;
        return keywords.equals(otherNameOrIdentityNumberContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
