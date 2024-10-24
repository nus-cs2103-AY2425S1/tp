package keycontacts.model.lesson;

import static java.util.Objects.requireNonNull;
import static keycontacts.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Student's lesson time in the student directory.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time implements Comparable<Time> {

    public static final String MESSAGE_CONSTRAINTS =
            "Lesson time should be in 24 hour format (e.g. 16:00)";

    /*
     * 24-hour time format.
     */
    public static final String VALIDATION_REGEX = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

    public final LocalTime value;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.value = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
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
        return value.equals(otherTime.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Time other) {
        return value.compareTo(other.value);
    }

    /**
     * Returns if the other {@code Time} object is after this time.
     */
    public boolean isAfter(Time other) {
        return value.isAfter(other.value);
    }

    /**
     * Returns if the other {@code Time} object is before this time.
     */
    public boolean isBefore(Time other) {
        return value.isBefore(other.value);
    }
}
