package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
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
        if (person.getRoles() == null) {
            return false;
        }
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

    public String getRolesAsString() {
        return roles.stream()
                .map(Role::toString)
                .collect(Collectors.joining(" "));
    }

    @Override
    public int hashCode() {
        return roles.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("roles", roles).toString();
    }
}
