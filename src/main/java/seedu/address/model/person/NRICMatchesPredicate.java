package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

public class NRICMatchesPredicate implements Predicate<Person> {

    private final Nric nric;

    public NRICMatchesPredicate(Nric nric) {
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

        if (!(other instanceof NRICMatchesPredicate)) {
            return false;
        }

        NRICMatchesPredicate predicate = (NRICMatchesPredicate) other;
        return nric.equals(predicate.nric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
