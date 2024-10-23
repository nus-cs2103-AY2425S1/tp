package seedu.academyassist.model.person;

import java.util.function.Predicate;

import seedu.academyassist.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Ic} matches the given NRIC.
 */
public class IcMatchesPredicate implements Predicate<Person> {
    private final Ic nric;

    public IcMatchesPredicate(Ic nric) {
        this.nric = nric;
    }

    @Override
    public boolean test(Person person) {
        return person.getIc().equals(nric);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IcMatchesPredicate otherIcMatchesPredicate)) {
            return false;
        }

        return this.nric.equals(otherIcMatchesPredicate.nric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("NRIC", nric).toString();
    }
}
