package keycontacts.model.lesson;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Student's lesson time in the student directory.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Lesson time should be in 24 hour format (e.g. 16:00)";

    /*
     * 24-hour time format.
     */
    public static final String VALIDATION_REGEX = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    private final LocalTime time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return time.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Time)) {
            return false;
        }

        Time otherTime = (Time) other;
        return time.equals(otherTime.time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    /**
     * Returns if the other {@code Time} object is after this time.
     */
    public boolean isAfter(Time other) {
        return time.isAfter(other.time);
    }

    /**
     * Returns if the other {@code Time} object is before this time.
     */
    public boolean isBefore(Time other) {
        return time.isBefore(other.time);
    }
}
