package seedu.address.model.event;

import seedu.address.model.person.Name;

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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        EventName otherName = (EventName) other;
        return eventName.equals(otherName.eventName);
    }

    @Override
    public int hashCode() {
        return eventName.hashCode();
    }
}
