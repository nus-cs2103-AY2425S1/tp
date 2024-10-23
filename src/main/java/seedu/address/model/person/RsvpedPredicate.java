package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code isRsvp} is true.
 */
public class RsvpedPredicate implements Predicate<Person> {
    private final RsvpStatus statusToCheck;

    public RsvpedPredicate(RsvpStatus statusToCheck) {
        this.statusToCheck = statusToCheck;
    }

    @Override
    public boolean test(Person person) {
        return person.getRsvpStatus().equals(statusToCheck);
    }
}
