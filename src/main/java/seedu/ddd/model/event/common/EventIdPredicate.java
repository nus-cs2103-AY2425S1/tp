package seedu.ddd.model.event.common;

import java.util.function.Predicate;

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
}
