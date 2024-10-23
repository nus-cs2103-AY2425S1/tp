package seedu.ddd.model.event.common.predicate;

import java.util.function.Predicate;

import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;


/**
 * Tests that a {@Code Event} 's {@code Id} matches the ID.
 */
public class EventIdPredicate implements Predicate<Event> {
    private final EventId eventId;

    public EventIdPredicate(EventId eventId) {
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
}
