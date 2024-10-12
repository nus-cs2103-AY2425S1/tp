package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents the time of a delivery in the application.
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS =
            "Time should be in the format dd-MM-yyyy HH:mm or dd-MM-yyyy, and must not be blank.";

    public static final DateTimeFormatter FORMATTER_TIME =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public final LocalDateTime time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time following the format of dd-MM-yyyy HH:mm.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        this.time = parseTime(time.trim());
    }

    public static boolean isValidTime(String test) {
        return canParse(test, FORMATTER_TIME);
    }

    private static boolean canParse(String time, DateTimeFormatter formatter) {
        try {
            LocalDateTime.parse(time, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private static LocalDateTime parseTime(String time) {
        if (canParse(time, FORMATTER_TIME)) {
            return LocalDateTime.parse(time, FORMATTER_TIME);
        } else {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS); //Should not reach here due to previous validation
        }
    }

    @Override
    public String toString() {
        return time.format(FORMATTER_TIME);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

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
}
