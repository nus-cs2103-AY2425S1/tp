package seedu.address.model.event;

/**
 * Represents an Event's name in the event list.
 */
public class EventName {
    public static final String MESSAGE_CONSTRAINTS = "Event names should "
            + "only contain alphanumeric characters and spaces, and it should not be blank";

    public final String eventName;

    /**
     * Constructs a {@code EventName}.
     *
     * @param eventName A valid event name.
     */
    public EventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return eventName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventName // instanceof handles nulls
                && eventName.equals(((EventName) other).eventName)); // state check
    }

    @Override
    public int hashCode() {
        return eventName.hashCode();
    }
}
