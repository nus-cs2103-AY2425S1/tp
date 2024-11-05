package seedu.address.model.wedding;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Wedding} is contained in a person under their wedding jobs or own wedding.
 */
public class PersonHasWeddingPredicate implements Predicate<Wedding> {
    private final Person person;

    /**
     * Constructs a PersonHasWeddingPredicate with the given wedding
     *
     * @param person The person object to check against
     */
    public PersonHasWeddingPredicate(Person person) {
        this.person = person;
    }

    /**
     * Tests if a given wedding is contained by the person
     * Returns true only if wedding is found in the person as its {@code ownWedding} or {@code weddingJobs}.
     *
     * @param wedding The wedding to test
     * @return true if the wedding is found in the person, false otherwise
     */
    @Override
    public boolean test(Wedding wedding) {
        return (person.getOwnWedding() != null && person.getOwnWedding().equals(wedding))
                || person.containsWeddingJob(wedding);
    }

    /**
     * Compares this predicate with another object for equality.
     * Two PersonHasWeddingPredicate are equal if they have the same person.
     *
     * @param other The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonHasWeddingPredicate)) {
            return false;
        }

        PersonHasWeddingPredicate otherPersonHasWeddingPredicate = (PersonHasWeddingPredicate) other;
        return person.equals(otherPersonHasWeddingPredicate.person);
    }

    /**
     * Returns a string representation of this PersonHasWeddingPredicate.
     * The string includes the person being matched against.
     *
     * @return A string representation of this predicate
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("person", person)
                .toString();
    }

}
