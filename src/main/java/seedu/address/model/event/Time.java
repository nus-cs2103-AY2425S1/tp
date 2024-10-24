package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public final LocalTime eventTime;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time in HH:MM.
     */
    public Time(String time) throws DateTimeParseException {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.eventTime = LocalTime.parse(time);
    }

    /**
     * Returns true if the given string is a valid time in HH:mm format.
     */
    public static boolean isValidTime(String test) {
        try {
            LocalTime.parse(test, TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    /**
     * Returns true if this time is before the other time.
     * @param other The other time to compare with.
     * @return True if this time is before the other time.
     */
    public boolean isBefore(Time other) {
        return eventTime.isBefore(other.eventTime);
    }


    @Override
    public String toString() {
        return eventTime.toString();
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
        return eventTime.equals(otherTime.eventTime);
    }

    @Override
    public int hashCode() {
        return eventTime.hashCode();
    }

}
