package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.wedding.Wedding;

/**
 * Tests that a {@code Person}'s {@code Wedding} matches the given wedding.
 */
public class ClientMatchesWeddingPredicate implements Predicate<Person> {
    private final Wedding wedding;

    /**
     * Constructs a ClientMatchesWeddingPredicate with the given wedding
     *
     * @param wedding The wedding to check against
     */
    public ClientMatchesWeddingPredicate(Wedding wedding) {
        this.wedding = wedding;
    }

    public Wedding getWedding() {
        return wedding;
    }

    /**
     * Tests if a given person is the client of the wedding.
     * Returns true only if the person is the client tof the wedding.
     *
     * @param person The wedding to test
     * @return true if the person is the client tof the wedding, false otherwise
     */
    @Override
    public boolean test(Person person) {
        return person.getOwnWedding() != null && person.getOwnWedding().equals(wedding);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientMatchesWeddingPredicate)) {
            return false;
        }

        ClientMatchesWeddingPredicate otherWeddingPredicate = (ClientMatchesWeddingPredicate) other;
        return wedding.equals(otherWeddingPredicate.wedding);
    }
}

