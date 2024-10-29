package seedu.address.logic.parser;

import java.util.List;

import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Nric} matches any of the keywords given.
 */
public class NricSearchCriteria implements SearchCriteria {

    private final List<String> nrics;

    public NricSearchCriteria(List<String> nrics) {
        this.nrics = nrics;
    }
    @Override
    public boolean test(Person person) {
        return nrics.stream().anyMatch(nric -> person.hasNric(new Nric(nric)));
    }
    @Override
    public String toString() {
        return "NricCriteria{ages=" + nrics + "}";
    }
}
