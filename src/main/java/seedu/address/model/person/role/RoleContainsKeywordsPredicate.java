package seedu.address.model.person.role;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Role} matches any of the keywords given.
 */
public class RoleContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public RoleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        // check if any role contains keyword
        return keywords.stream()
                .anyMatch(keywords -> StringUtil.containsWordIgnoreCase(person.getRoles().stream()
                                .map(Role::getRoleName)
                                .reduce("", (x, y) -> x + " " + y), keywords));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoleContainsKeywordsPredicate // instanceof handles nulls
                    && keywords.equals(((RoleContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
