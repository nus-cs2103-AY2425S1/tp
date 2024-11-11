package seedu.ddd.model.event.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.event.common.Event;


/**
 * Tests that a {@Code Event} 's {@code Id} matches the ID.
 */
public class EventIdPredicate implements Predicate<Event> {
    private final Id eventId;

    public EventIdPredicate(Id eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean test(Event event) {
        return this.eventId.equals(event.getEventId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventIdPredicate)) {
            return false;
        }

        EventIdPredicate otherEventIdPredicate = (EventIdPredicate) other;
        return eventId.equals(otherEventIdPredicate.eventId);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("eventId", eventId).toString();
    }
}
