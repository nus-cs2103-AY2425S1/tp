package hallpointer.address.model.session;

import static hallpointer.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a date in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format dd MMM yyyy (e.g. 24 Sep 2024)";
    
    // Desired date format
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public final LocalDate fullDate;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date string.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        fullDate = parseDate(date);
    }

    /**
     * Returns true if a given string is a valid date according to the specified format.
     */
    public static boolean isValidDate(String test) {
        try {
            DATE_FORMATTER.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses a valid date string into a LocalDate object.
     * @param date A valid date string.
     * @return A LocalDate object.
     */
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    /**
     * Returns the date as a formatted string.
     */
    @Override
    public String toString() {
        return fullDate.format(DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return fullDate.equals(otherDate.fullDate);
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }
}
