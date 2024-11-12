package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.wedding.Wedding;

/**
 * Tests that a {@code Person}'s {@code Wedding} matches the given wedding.
 */
public class PersonMatchesWeddingPredicate implements Predicate<Person> {
    private final Wedding wedding;

    /**
     * Constructs a PersonMatchesWeddingPredicate with the given wedding
     *
     * @param wedding The wedding to check against
     */
    public PersonMatchesWeddingPredicate(Wedding wedding) {
        this.wedding = wedding;
    }

    public Wedding getWedding() {
        return wedding;
    }

    /**
     * Tests if a given person is associated with the wedding
     * Returns true only if person is either the client of the wedding, or has weddingJobs with the wedding.
     *
     * @param person The person to test
     * @return true if the person is associated with the wedding, false otherwise
     */
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
