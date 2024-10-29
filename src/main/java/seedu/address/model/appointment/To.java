package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the end time of an appointment in the address book.
 * The {@code To} class encapsulates the end time as a string.
 * This value cannot be null.
 */
public class To {
    public static final To EMPTY_TO = new To(LocalTime.MIN);
    public static final String MESSAGE_CONSTRAINTS =
            "Times should be in the format HH:mm or HHmm, e.g., 0900 or 09:00.";
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
        checkArgument(isValidTime(value), MESSAGE_CONSTRAINTS); // Validate using checkArgument
        this.value = parseTime(value);
    }

    private To(LocalTime value) {
        this.value = value;
    }

    /**
     * Parses the time string into a LocalTime.
     */
    private LocalTime parseTime(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            if (time.matches("\\d{4}")) {
                time = time.substring(0, 2) + ":" + time.substring(2);
            }
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks if the time string is in a valid format.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof To
                && value.equals(((To) other).value));
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
