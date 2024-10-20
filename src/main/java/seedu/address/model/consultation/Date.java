package seedu.address.model.consultation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Represents a Date in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS = "Dates should be in the format YYYY-MM-DD, "
            + "and must be a valid date (e.g., no month 13 or day 32).";

    private final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date string.
     * @throws IllegalArgumentException if the given {@code date} is not a valid date.
     */
    public Date(String date) {
        if (!isValidDate(date)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.value = date;
    }

    /**
     * Returns the value of the date as a string.
     *
     * @return The string representation of the date.
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns true if a given string is a valid date format (YYYY-MM-DD) and represents a real date.
     *
     * @param test The string to test for validity.
     * @return True if the string represents a valid date, false otherwise.
     */
    public static boolean isValidDate(String test) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate.parse(test, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
