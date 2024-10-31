package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Represents a delivery's order time.
 * Guarantees: is valid as declared in {@link #isValidTime(String)} (String)}
 */
public class Time {
    public static final String MESSAGE_CONSTRAINTS =
            "Incorrect time format. Expected format: hh:mm:ss";
    public final LocalTime value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        value = LocalTime.parse(time);
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        try {
            LocalTime parsedTime = LocalTime.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Time Ordered: " + value;
    }
}
