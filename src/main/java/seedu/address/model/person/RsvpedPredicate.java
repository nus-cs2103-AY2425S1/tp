package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code isRsvp} is true.
 */
public class RsvpedPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return person.getRsvp();
    }

}
