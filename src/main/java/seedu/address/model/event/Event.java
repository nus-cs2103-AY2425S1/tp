package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    private final EventName eventName;
    private final EventDescription eventDescription;
    private final EventDuration eventDuration;
    private final int eventId;

    /**
     * Constructs an {@code Event}. ID is initialised to -1.
     *
     * @param eventName A valid event name.
     * @param eventDescription A valid event description.
     * @param eventDuration A valid event duration with a start and end date.
     */
    public Event(EventName eventName, EventDescription eventDescription, EventDuration eventDuration) {
        requireNonNull(eventName);
        requireNonNull(eventDescription);
        requireNonNull(eventDuration);
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDuration = eventDuration;
        this.eventId = -1;
    }

    /**
     * Constructs an {@code Event}.
     *
     * @param eventName A valid event name.
     * @param eventDescription A valid event description.
     * @param eventDuration A valid event duration with a start and end date.
     */
    public Event(EventName eventName, EventDescription eventDescription, EventDuration eventDuration, int eventId) {
        requireNonNull(eventName);
        requireNonNull(eventDescription);
        requireNonNull(eventDuration);
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDuration = eventDuration;
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event otherEvent)) {
            return false;
        }

        boolean sameName = eventName.equals(otherEvent.eventName);
        boolean sameDescription = eventDescription.equals(otherEvent.eventDescription);
        boolean sameDuration = eventDuration.equals(otherEvent.eventDuration);
        return sameName && sameDescription && sameDuration;
    }

    @Override
    public int hashCode() {
        return eventName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("eventName", eventName)
                .add("eventDescription", eventDescription)
                .add("eventDuration", eventDuration)
                .toString();
    }

    public EventName getEventName() {
        return eventName;
    }

    public EventDescription getEventDescription() {
        return eventDescription;
    }

    public LocalDate getEventStartDate() {
        return eventDuration.getStartDate();
    }

    public LocalDate getEventEndDate() {
        return eventDuration.getEndDate();
    }

    public EventDuration getEventDuration() {
        return eventDuration;
    }

    public int getEventId() {
        return eventId;
    }

    /**
     * Returns a new {@code Event} object that has the same attributes except the ID.
     */
    public Event changeId(int newId) {
        return new Event(eventName, eventDescription, eventDuration, newId);
    }

    /**
     * Returns true if both event have same name.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName());
    }
}
