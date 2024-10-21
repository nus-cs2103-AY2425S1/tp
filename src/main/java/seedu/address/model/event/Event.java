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
    public final String value;

    /**
     * Constructs an {@code Event}
     * @param event A valid event.
     */
    public Event(String event) {
        requireNonNull(event);
        checkArgument(isValidEvent(event), MESSAGE_CONSTRAINTS);
        value = event;
    }

    /**
     * returns true if a given string is a valid event.
     */
    public static boolean isValidEvent(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
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

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
