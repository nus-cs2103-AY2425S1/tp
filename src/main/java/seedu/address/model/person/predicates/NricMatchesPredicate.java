package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code NRIC} matches the given NRIC exactly.
 */
public class NricMatchesPredicate implements Predicate<Person> {
    private final String nric;

    public NricMatchesPredicate(String nric) {
        this.nric = nric;
    }

    @Override
    public boolean test(Person person) {
        return person.getNric().value.equalsIgnoreCase(nric);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NricMatchesPredicate)) {
            return false;
        }

        NricMatchesPredicate otherNricMatchesPredicate = (NricMatchesPredicate) other;
        return nric.equalsIgnoreCase(otherNricMatchesPredicate.nric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("nric", nric).toString();
    }
}
