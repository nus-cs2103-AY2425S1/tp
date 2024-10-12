package seedu.address.model.event;

/**
 * Represents an Event name in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class EventName {
    private final String eventName;

    /**
     * Constructs a {@code Name}.
     *
     * @param eventName A valid name.
     */
    public EventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public String toString() {
        return eventName;
    }
}
