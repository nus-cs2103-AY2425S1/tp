package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



/**
 * Represents the start time of an appointment in the address book.
 * The {@code From} class encapsulates the start time as a string.
 * This value cannot be null.
 */
public class From {
    public static final From EMPTY_FROM = new From(LocalTime.MIN);
    public static final String MESSAGE_CONSTRAINTS =
            "Times should be in the format HH:mm or HHmm, e.g., 0900 or 09:00.";
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
        checkArgument(isValidTime(value), MESSAGE_CONSTRAINTS);
        this.value = parseTime(value);
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

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof From
                && value.equals(((From) other).value));
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
