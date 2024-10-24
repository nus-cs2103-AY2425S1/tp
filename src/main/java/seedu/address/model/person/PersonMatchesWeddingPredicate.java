package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.wedding.Wedding;

/**
 * Tests that a {@code Person}'s {@code Wedding} matches the given wedding.
 */
public class PersonMatchesWeddingPredicate implements Predicate<Person> {
    private final Wedding wedding;

    public PersonMatchesWeddingPredicate(Wedding wedding) {
        this.wedding = wedding;
    }

    public Wedding getWedding() {
        return wedding;
    }

    @Override
    public boolean test(Person person) {
        return (person.getOwnWedding() != null && person.getOwnWedding().equals(wedding))
                || person.getWeddingJobs().contains(wedding);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonMatchesWeddingPredicate)) {
            return false;
        }

        PersonMatchesWeddingPredicate otherWeddingPredicate = (PersonMatchesWeddingPredicate) other;
        return wedding.equals(otherWeddingPredicate.wedding);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("wedding", wedding)
                .toString();
    }
}
