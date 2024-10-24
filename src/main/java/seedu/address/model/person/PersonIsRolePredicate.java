package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.role.Role;

/**
 * Tests that a {@code Person}'s {@code role} matches any of the role keywords given.
 */
public class PersonIsRolePredicate implements Predicate<Person> {
    private final List<Role> roles;

    public PersonIsRolePredicate(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean test(Person person) {
        return roles.stream()
                .anyMatch(role -> person.getRoles().contains(role));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonIsRolePredicate)) {
            return false;
        }

        PersonIsRolePredicate otherPersonIsRolePredicate = (PersonIsRolePredicate) other;
        return roles.equals(otherPersonIsRolePredicate.roles);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("roles", roles).toString();
    }
}
