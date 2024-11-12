package seedu.address.logic.parser.criteria;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameSearchCriteria implements SearchCriteria {

    private final Set<Name> names;

    /**
     * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
     */
    public NameSearchCriteria(Collection<String> names) {
        this.names = names.stream()
                .map(Name::new)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean test(Person person) {
        return names.stream().anyMatch(person::hasName);
    }

    @Override
    public String toString() {
        return "NameCriteria{names=" + names + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof NameSearchCriteria)) {
            return false;
        }

        NameSearchCriteria otherNameSearchCriteria = (NameSearchCriteria) other;
        return names.equals(otherNameSearchCriteria.names);
    }
}
