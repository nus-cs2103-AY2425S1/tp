package seedu.address.logic.parser;

import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

import java.util.List;

public class RoleSearchCriteria implements SearchCriteria {

    private final List<String> keywords;

    public RoleSearchCriteria(List<String> keywords) {
        this.keywords = keywords;
    }
    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(role -> person.hasRole(Role.valueOf(role.toUpperCase())));
    }
}
