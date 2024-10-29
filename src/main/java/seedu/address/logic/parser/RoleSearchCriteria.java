package seedu.address.logic.parser;

import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Tests that a {@code Person}'s {@code Role} matches any of the keywords given.
 */
public class RoleSearchCriteria implements SearchCriteria {

    private final List<String> roles;

    public RoleSearchCriteria(List<String> roles) {
        this.roles = roles;
    }
    @Override
    public boolean test(Person person) {
        return roles.stream().anyMatch(role -> person.hasRole(Role.valueOf(role.toUpperCase())));
    }

    @Override
    public String toString() {
        return "RoleCriteria{ages=" + roles + "}";
    }
}
