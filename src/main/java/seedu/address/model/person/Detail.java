package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Represents additional details of a person, including a specific time format.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)} and {@link #isValidTime(String)}
 */
public class Detail {
    public static final String MESSAGE_CONSTRAINTS =
            "Details can be any characters. Time must follow the format DD-MM-YYYY HHMM.";

    public static final String TIME_FORMAT = "dd-MM-yyyy HHmm";

    public final String value;

    /**
     * Constructs a {@code Detail}.
     *
     * @param detail A valid detail (any string or time in the format DD-MM-YYYY HHMM).
     */
    public Detail(String detail) {
        requireNonNull(detail);
        checkArgument(isValidDetail(detail), MESSAGE_CONSTRAINTS);
        this.value = detail;
    }

    /**
     * Returns true if the given string is a valid detail.
     * A valid detail can be any character, and if it's a time, it must match the format DD-MM-YYYY HHMM.
     */
    public static boolean isValidDetail(String test) {
        // Check if the test string follows the time format first
        if (isValidTime(test)) {
            return true;
        }
        // If not a time, any other string is considered valid
        return true;
    }

    /**
     * Returns true if the given string is a valid time in the format DD-MM-YYYY HHMM.
     */
    public static boolean isValidTime(String test) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
            LocalDateTime.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Detail)) {
            return false;
        }

        Detail otherDetail = (Detail) other;
        return value.equals(otherDetail.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
