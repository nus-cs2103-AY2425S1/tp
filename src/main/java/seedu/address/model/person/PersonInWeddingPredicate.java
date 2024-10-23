package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.wedding.Wedding;

/**
 * Tests that a {@code Person} is in a {@code Wedding}
 */
public class PersonInWeddingPredicate implements Predicate<Person> {
    private final Wedding wedding;

    public PersonInWeddingPredicate(Wedding wedding) {
        this.wedding = wedding;
    }

    @Override
    public boolean test(Person person) {
        PersonId personId = person.getId();
        return wedding.getAssignees().contains(personId);
    }
}
