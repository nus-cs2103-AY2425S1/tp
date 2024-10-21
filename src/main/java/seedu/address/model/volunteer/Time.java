package seedu.address.model.volunteer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Represents a Event's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 * Throws: DateTimeParseException if an invalid time is provided
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should follow the format hh:mm, it must be valid and not blank";
    public static final String MESSAGE_CHRONOLOGICAL_CONSTRAINTS =
            "Start time should be before end time";

    public final LocalTime volunteerTime;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time in HH:MM.
     */
    public Time(String time) throws DateTimeParseException {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.volunteerTime = LocalTime.parse(time);
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        // Handle null time
        if (test == null) {
            throw new NullPointerException();
        }

        // Handle blank time and invalid formats
        if (test.isBlank() || !test.matches("\\d{2}:\\d{2}")) {
            return false;
        }

        // Split the string to validate hours and minutes
        String[] parts = test.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);

        // Check valid range for hours and minutes
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return false;
        }

        // If all checks pass, it's valid
        return true;
    }


    /**
     * Returns true if this time is before the other time.
     * @param other The other time to compare with.
     * @return True if this time is before the other time.
     */
    public boolean isBefore(Time other) {
        return volunteerTime.isBefore(other.volunteerTime);
    }


    @Override
    public String toString() {
        return volunteerTime.toString();
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
        return volunteerTime.equals(otherTime.volunteerTime);
    }

    @Override
    public int hashCode() {
        return volunteerTime.hashCode();
    }

}
