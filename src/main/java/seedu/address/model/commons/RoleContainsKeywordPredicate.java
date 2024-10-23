package seedu.address.model.commons;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Tests that a {@code Person}'s {@code Role} matches the role given.
 */
public class RoleContainsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public RoleContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return new Role(keyword).equals(person.getRole());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoleContainsKeywordPredicate)) {
            return false;
        }

        RoleContainsKeywordPredicate otherRoleContainsKeywordPredicate = (RoleContainsKeywordPredicate) other;
        return new Role(keyword).equals(new Role(otherRoleContainsKeywordPredicate.keyword));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
