package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the end time of an appointment in the address book.
 * The {@code To} class encapsulates the end time as a string.
 * This value cannot be null.
 */
public class To {
    public static final To EMPTY_TO = new To(LocalTime.MIN);
    private static final String VALIDATION_REGEX = "\\d{4}|\\d{2}:\\d{2}";

    public final LocalTime value;

    /**
     * Constructs a {@code To} object with the specified end time value.
     * The value must not be {@code null}.
     *
     * @param value A string representing the end time.
     */
    public To(String value) {
        requireNonNull(value);
        checkArgument(isValidTime(value), AppointmentUtil.TIME_MESSAGE_CONSTRAINTS); // Validate using checkArgument
        this.value = AppointmentUtil.parseTime(value);
    }

    private To(LocalTime value) {
        this.value = value;
    }

    /**
     * Checks if the time string is in a valid format.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof To)) {
            return false;
        }

        To otherTo = (To) other;

        return this.value.equals(otherTo.value);
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
