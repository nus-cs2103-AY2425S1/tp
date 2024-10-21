package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event in the address book.
 * Guarentees: immutable, is valid as declared in {@link #isValidEvent(String)}
 */
public class Event {
    public static final String MESSAGE_CONSTRAINTS = "Events can take any values, and it should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final EventName value;

    /**
     * Constructs an {@code Event}
     */
    public Event(String eventName) {
        requireNonNull(eventName);
        checkArgument(isValidEvent(eventName), MESSAGE_CONSTRAINTS);
        this.value = new EventName(eventName);
    }


    /**
     * returns true if a given string is a valid event.
     */
    public static boolean isValidEvent(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Returns the value of the event.
     */
    public String getValue() {
        return value.toString();
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

        return value.equals(otherEvent.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
