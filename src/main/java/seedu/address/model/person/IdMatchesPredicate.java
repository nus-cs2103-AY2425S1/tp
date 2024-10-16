package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Ic} matches the given NRIC.
 */
public class IdMatchesPredicate implements Predicate<Person> {
    private final Ic nric;

    public IdMatchesPredicate(Ic nric) {
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
        if (!(other instanceof IdMatchesPredicate otherIdMatchesPredicate)) {
            return false;
        }

        return this.nric.equals(otherIdMatchesPredicate.nric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("NRIC", nric).toString();
    }
}
