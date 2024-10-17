package seedu.address.model.volunteer;

import seedu.address.commons.util.ToStringBuilder;

import java.util.function.Predicate;

public class VolunteerInvolvedInEventPredicate implements Predicate<Volunteer> {

    private final String eventName;

    public VolunteerInvolvedInEventPredicate(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public boolean test(Volunteer volunteer) {
        return volunteer.isInvolvedInEvent(eventName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerInvolvedInEventPredicate)) {
            return false;
        }

        VolunteerInvolvedInEventPredicate otherVolInEventPredicate = (VolunteerInvolvedInEventPredicate) other;
        return eventName.equals(otherVolInEventPredicate.eventName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("event name", eventName).toString();
    }

}
