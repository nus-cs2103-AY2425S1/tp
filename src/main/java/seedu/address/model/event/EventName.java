package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EventName {

    public static final String MESSAGE_CONSTRAINTS =
            "Event names should only contain alphanumeric characters and spaces, it should not be blank, "
                    + "and it should start with an alphabet";

    /*
     * The first character of the name must be an alphabet.
     */
    public static final String VALIDATION_REGEX = "^[A-Za-z][\\p{Alnum} ]*$";

    public final String eventName;

    /**
     * Constructs a {@code EventName}.
     *
     * @param eventName A valid Event name.
     */
    public EventName(String eventName) {
        requireNonNull(eventName);
        checkArgument(isValidName(eventName), MESSAGE_CONSTRAINTS);
        this.eventName = eventName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof EventName)) {
            return false;
        }

        EventName otherEventName = (EventName) other;
        return eventName.equals(otherEventName.eventName);
    }

    /**
     * Returns true if 2 event names are equal (case-insensitive).
     */
    public boolean equalsLowerCase(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventName)) {
            return false;
        }

        EventName otherEventName = (EventName) other;
        return eventName.equalsIgnoreCase(otherEventName.eventName);
    }

    @Override
    public int hashCode() {
        return eventName.hashCode();
    }

}
