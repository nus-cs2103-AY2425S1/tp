package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;


/**
 * Tests that a {@code Person} is in the {@code Event} given.
 */
public class PersonInEventPredicate implements Predicate<Person> {
    private final Event event;

    public PersonInEventPredicate(Event event) {
        this.event = event;
    }

    @Override
    public boolean test(Person person) {
        return this.event.isPersonInEvent(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonInEventPredicate)) {
            return false;
        }

        PersonInEventPredicate otherPersonInEventPredicate = (PersonInEventPredicate) other;
        return event.equals(otherPersonInEventPredicate.event);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("Event", event).toString();
    }
}
