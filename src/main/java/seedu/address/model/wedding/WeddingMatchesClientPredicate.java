package seedu.address.model.wedding;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Wedding} matches the given wedding.
 */
public class WeddingMatchesClientPredicate implements Predicate<Wedding> {
    private final Person person;

    public WeddingMatchesClientPredicate(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

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
