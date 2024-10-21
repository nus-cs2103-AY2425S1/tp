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
            "Time should follow the format hh:mm, and it should not be blank";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\d{2}:\\d{2}";

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
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            LocalTime.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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
