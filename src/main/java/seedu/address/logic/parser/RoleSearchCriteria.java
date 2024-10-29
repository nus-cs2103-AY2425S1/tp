package seedu.address.logic.parser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Tests that a {@code Person}'s {@code Role} matches any of the keywords given.
 */
public class RoleSearchCriteria implements SearchCriteria {

    private final Set<Role> roles;

    public RoleSearchCriteria(List<String> roles) {
        this.roles = roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }
    @Override
    public boolean test(Person person) {
        return roles.stream().anyMatch(person::hasRole);
    }

    @Override
    public String toString() {
        return "RoleCriteria{ages=" + roles + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof RoleSearchCriteria)) {
            return false;
        }

        RoleSearchCriteria otherRoleSearchCriteria = (RoleSearchCriteria) other;
        return roles.equals(otherRoleSearchCriteria.roles);
    }

}
