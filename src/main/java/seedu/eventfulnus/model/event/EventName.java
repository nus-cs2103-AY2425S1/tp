package seedu.eventfulnus.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.eventfulnus.commons.util.AppUtil.checkArgument;

/**
 * Represents an {@link Event}'s name in the event list of the address book. Guarantees: immutable;
 * is valid as declared in {@link #isValidEventName(String)}
 */
public class EventName {
    public static final String MESSAGE_CONSTRAINTS = "Event names should "
            + "only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "\\S.*";

    private final String eventName;

    /**
     * Constructs a {@code EventName}.
     *
     * @param eventName A valid event name.
     */
    public EventName(String eventName) {
        requireNonNull(eventName);
        checkArgument(isValidEventName(eventName), MESSAGE_CONSTRAINTS);
        this.eventName = eventName;
    }

    public static boolean isValidEventName(String test) {
        return test.matches(VALIDATION_REGEX);
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
