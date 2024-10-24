package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

/**
 * An abstract class to represent a Person's date data in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public abstract class Date {

    public static final String DATE_FORMAT_STRING = "d MMM yyyy";

    public static final String MESSAGE_CONSTRAINTS =
            String.format("Date should follow %s format, where %s is a valid date (e.g. 31 dec 2019, 1 Dec 1999)",
                    DATE_FORMAT_STRING, DATE_FORMAT_STRING);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern(DATE_FORMAT_STRING)
            .toFormatter();

    private final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A case-insensitive valid date string
     */
    public Date(String date) {
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        // additional parsing required to ensure string format of month is consistent
        // i.e. 30 Jun 2019, not 30 jun 2019
        value = parseLocalDate(date).format(DATE_TIME_FORMATTER);
    }

    /**
     * Returns value of {@code Date}.
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * Converts {@code Date} value to {@code LocalDate}
     *
     * @return converted LocalDate object
     */
    public final LocalDate toLocalDate() {
        return LocalDate.parse(value, DATE_TIME_FORMATTER);
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

        if (!(other instanceof Date otherDate)) {
            return false;
        }

        LocalDate thisLocalDate = this.toLocalDate();
        LocalDate otherLocalDate = otherDate.toLocalDate();
        return thisLocalDate.equals(otherLocalDate);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns true if a given string is a valid date.
     *
     * @param test A string to be tested
     * @return {@code true} if the string is valid, {@code false} otherwise
     */
    public static boolean isValidDate(String test) {
        requireNonNull(test);

        try {
            parseLocalDate(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses date string to LocalDate.
     *
     * @param date A case-insensitive date string with the format "d MMM yyyy"
     * @return LocalDate
     * @throws DateTimeParseException if string is invalid
     */
    public static LocalDate parseLocalDate(String date) throws DateTimeParseException {
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }
}
