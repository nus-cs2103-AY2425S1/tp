package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Nric} matches the given NRIC.
 */
public class NricMatchesPredicate implements Predicate<Person> {

    private final Nric nric;

    /**
     * Creates a NricMatchesPredicate to test if a person's NRIC matches the given NRIC.
     *
     * @param nric The NRIC to test against.
     */
    public NricMatchesPredicate(Nric nric) {
        this.nric = nric;
    }

    @Override
    public boolean test(Person person) {
        return person.getNric().equals(nric);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof NricMatchesPredicate)) {
            return false;
        }

        NricMatchesPredicate predicate = (NricMatchesPredicate) other;
        return nric.equals(predicate.nric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("nric", nric).toString();
    }
}
