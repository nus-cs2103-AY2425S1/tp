package seedu.address.model.wedding;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Wedding} matches the given wedding.
 */
public class WeddingMatchesClientPredicate implements Predicate<Wedding> {
    private final Person person;

    /**
     * Constructs a WeddingMatchesClientPredicate with the given person
     *
     * @param person The person object to check against
     */
    public WeddingMatchesClientPredicate(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    /**
     * Tests if a person is the client of a given wedding
     * Returns true only if person is the client of the given wedding.
     *
     * @param wedding The wedding to test
     * @return true if the person is the client of the wedding, false otherwise
     */
    @Override
    public boolean test(Wedding wedding) {
        return person.getOwnWedding() != null && person.getOwnWedding().equals(wedding);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WeddingMatchesClientPredicate)) {
            return false;
        }

        WeddingMatchesClientPredicate other1 = (WeddingMatchesClientPredicate) other;
        return person.equals(other1.person);
    }
}
