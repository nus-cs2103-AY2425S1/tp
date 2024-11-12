package seedu.eventfulnus.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.eventfulnus.commons.util.StringUtil;
import seedu.eventfulnus.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> (StringUtil.containsSubstringIgnoreCase(person.getName().toString(), keyword)
                        || StringUtil.containsSubstringIgnoreCase(person.getPhone().value, keyword)
                        || StringUtil.containsSubstringIgnoreCase(person.getEmail().value, keyword)
                        || person.getRoles().stream().anyMatch(
                                role -> role.getRoleName().toLowerCase().contains(keyword.toLowerCase()))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate otherPersonContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherPersonContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
