package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Date in the internship book.
 * Guarantees: immutable; is valid as declared in {@link #validate(String)}.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in the format 'yyyy-MM-dd' and must be valid.";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LocalDate date;

    /**
     * Constructs a {@code Date} with a {@code LocalDate}.
     *
     * @param date A valid LocalDate object.
     * @throws NullPointerException if the {@code date} is null.
     */
    public Date(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    /**
     * Constructs a {@code Date} from a String.
     * The String must be in the format 'yyyy-MM-dd'.
     *
     * @param dateString A string representing a date.
     * @throws NullPointerException if the {@code dateString} is null.
     * @throws IllegalArgumentException if the {@code dateString} does not satisfy the format.
     */
    public Date(String dateString) {
        requireNonNull(dateString);
        checkArgument(Date.validate(dateString), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(dateString, FORMATTER);
    }

    /**
     * Returns the {@code LocalDate} representation of the date.
     *
     * @return the date as a {@code LocalDate} object.
     */
    public LocalDate getValue() {
        return this.date;
    }

    /**
     * Returns a string representation of the date using the 'yyyy-MM-dd' format.
     *
     * @return the formatted date as a string.
     */
    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    /**
     * Compares this date to another object.
     *
     * @param other the object to compare.
     * @return true if the object is an instance of {@code Date} and has the same value, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return date.equals(otherDate.date);
    }

    /**
     * Returns the hash code of the date.
     *
     * @return the hash code of the {@code LocalDate} value.
     */
    @Override
    public int hashCode() {
        return date.hashCode();
    }

    /**
     * Validates the given string as a date.
     * The string must be in the format 'yyyy-MM-dd'.
     *
     * @param test the string to be validated.
     * @return true if the string is a valid date, false otherwise.
     */
    public static boolean validate(String test) {
        try {
            LocalDate.parse(test, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
