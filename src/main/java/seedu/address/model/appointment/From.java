package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;



/**
 * Represents the start time of an appointment in the address book.
 * The {@code From} class encapsulates the start time as a string.
 * This value cannot be null.
 */
public class From {
    public static final From EMPTY_FROM = new From(LocalTime.MIN);
    private static final String VALIDATION_REGEX = "\\d{4}|\\d{2}:\\d{2}";

    public final LocalTime value;

    /**
     * Constructs a {@code From} object with the specified start time value.
     * The value must not be {@code null}.
     *
     * @param value A string representing the start time.
     */
    public From(String value) {
        requireNonNull(value);
        checkArgument(isValidTime(value), AppointmentUtil.TIME_MESSAGE_CONSTRAINTS);
        this.value = AppointmentUtil.parseTime(value);
    }

    private From(LocalTime value) {
        this.value = value;
    }

    /**
     * Checks if the time string is in the valid format.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof From)) {
            return false;
        }

        From otherFrom = (From) other;

        return this.value.equals(otherFrom.value);
    }

    @Override
    public String toString() {
        return value.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
