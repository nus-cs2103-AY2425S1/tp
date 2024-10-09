package seedu.address.model.person;

import java.util.function.Predicate;
import seedu.address.commons.util.ToStringBuilder;

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

        NricMatchesPredicate otherNRICMatchesPredicate = (NricMatchesPredicate) other;
        return nric.equalsIgnoreCase(otherNRICMatchesPredicate.nric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("nric", nric).toString();
    }
}
