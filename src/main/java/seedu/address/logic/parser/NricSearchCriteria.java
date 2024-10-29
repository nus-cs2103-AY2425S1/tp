package seedu.address.logic.parser;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Nric} matches any of the keywords given.
 */
public class NricSearchCriteria implements SearchCriteria {

    private final Set<Nric> nrics;

    public NricSearchCriteria(List<String> nrics) {
        this.nrics = nrics.stream().map(Nric::new).collect(Collectors.toSet());
    }
    @Override
    public boolean test(Person person) {
        return nrics.stream().anyMatch(person::hasNric);
    }
    @Override
    public String toString() {
        return "NricCriteria{ages=" + nrics + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof NricSearchCriteria)) {
            return false;
        }

        NricSearchCriteria otherNricSearchCriteria = (NricSearchCriteria) other;
        return nrics.equals(otherNricSearchCriteria.nrics);
    }
}
