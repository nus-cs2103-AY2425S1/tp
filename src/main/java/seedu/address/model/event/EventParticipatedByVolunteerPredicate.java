package seedu.address.model.event;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Event}'s {@code volunteers} contains the volunteer name given.
 */
public class EventParticipatedByVolunteerPredicate implements Predicate<Event> {
    private final String volunteerName;

    public EventParticipatedByVolunteerPredicate(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    @Override
    public boolean test(Event event) {
        return event.hasVolunteer(volunteerName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventParticipatedByVolunteerPredicate)) {
            return false;
        }

        EventParticipatedByVolunteerPredicate otherVolInEventPredicate = (EventParticipatedByVolunteerPredicate) other;
        return volunteerName.equalsIgnoreCase(otherVolInEventPredicate.volunteerName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("volunteer name", volunteerName).toString();
    }

}
