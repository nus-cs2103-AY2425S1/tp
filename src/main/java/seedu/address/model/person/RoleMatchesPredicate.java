package seedu.address.model.person;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Role} matches the specified role.
 */
public class RoleMatchesPredicate implements Predicate<Person> {
    private final Role role;

    public RoleMatchesPredicate(Role role) {
        this.role = role;
    }

    @Override
    public boolean test(Person person) {
        return person.isRole(this.role);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoleMatchesPredicate // instanceof handles nulls
                && role.equals(((RoleMatchesPredicate) other).role)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }

    @Override
    public String toString() {
        return "RoleMatchesPredicate{" + "role='" + role + '\'' + '}';
    }
}
