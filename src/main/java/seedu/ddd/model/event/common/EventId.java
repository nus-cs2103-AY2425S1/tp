package seedu.ddd.model.event.common;

import seedu.ddd.commons.util.AppUtil;
import seedu.ddd.commons.util.StringUtil;

/**
 * Represents a Event's ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventId(int)}}
 */
public class EventId {
    public static final String MESSAGE_CONSTRAINTS =
            "EventID should be a non-negative integer, and it should not be blank";

    public final int eventId;

    /**
     * Constructs a {@code EventId}.
     * @param eventId A valid int eventID.
     */
    public EventId(int eventId) {
        AppUtil.checkArgument(isValidEventId(eventId), MESSAGE_CONSTRAINTS);
        assert eventId >= 0;
        this.eventId = eventId;
    }

    /**
     * Constructs a {@code EventId}.
     * @param eventId A valid string id.
     */
    public EventId(String eventId) {
        AppUtil.checkArgument(isValidEventId(eventId), MESSAGE_CONSTRAINTS);
        this.eventId = Integer.parseInt(eventId);
    }


    /**
     * Returns true if a given integer is a valid event id.
     */
    public static boolean isValidEventId(int test) {
        return test >= 0;
    }

    /**
     * Returns true if a given string can be a valid event id.
     */
    public static boolean isValidEventId(String test) {
        return StringUtil.isUnsignedInteger(test);
    }

    @Override
    public String toString() {
        return String.valueOf(eventId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof EventId)) {
            return false;
        }

        EventId otherEventId = (EventId) other;
        return eventId == otherEventId.eventId;
    }

    @Override
    public int hashCode() {
        return eventId;
    }
}
