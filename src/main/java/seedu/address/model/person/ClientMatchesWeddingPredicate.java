package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.wedding.Wedding;

/**
 * Tests that a {@code Person}'s {@code Wedding} matches the given wedding.
 */
public class ClientMatchesWeddingPredicate implements Predicate<Person> {
    private final Wedding wedding;

    public ClientMatchesWeddingPredicate(Wedding wedding) {
        this.wedding = wedding;
    }

    public Wedding getWedding() {
        return wedding;
    }

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

