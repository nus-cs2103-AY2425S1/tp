package seedu.address.model.log;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents an appointment date in the log.
 */
public class AppointmentDate implements Comparable<AppointmentDate> {
    public static final String MESSAGE_CONSTRAINTS = "Invalid date format! Please use 'dd MMM yyyy'.";
    public static final String MESSAGE_CONSTRAINTS_INVALID_DATE = "Invalid date! Please enter a valid date.";
    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd MMM yyyy")
            .toFormatter(Locale.ENGLISH);

    private static final DateTimeFormatter STRICT_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd MMM uuuu")
            .toFormatter(Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    private final LocalDate date;

    /**
     * Constructs an {@code AppointmentDate} with the specified {@code LocalDate}.
     *
     * @param date A valid LocalDate.
     */
    public AppointmentDate(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    /**
     * Constructs an {@code AppointmentDate} by parsing a date string.
     *
     * @param dateString A valid date string in the format of "dd MMM yyyy".
     */
    public AppointmentDate(String dateString) {
        requireNonNull(dateString);
        checkArgument(isValidDate(dateString), MESSAGE_CONSTRAINTS_INVALID_DATE);
        this.date = LocalDate.parse(dateString, STRICT_FORMATTER);
    }

    /**
     * Returns the appointment date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns true if the given string is a valid date string in the format "dd MMM yyyy".
     *
     * @param dateString the string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDateString(String dateString) {
        requireNonNull(dateString);
        try {
            LocalDate.parse(dateString, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if the given string is a valid date.
     *
     * @param dateString the string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateString) {
        requireNonNull(dateString);
        try {
            LocalDate.parse(dateString, STRICT_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if the given string is a valid day.
     *
     * @param dateString the string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDay(String dateString) {
        requireNonNull(dateString);
        if (extractDay(dateString) > 99 || extractDay(dateString) < 0) {
            return true;
        }
        if (extractDay(dateString) < 1 || extractDay(dateString) > 31) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the given string has a valid day.
     *
     * @param dateString the string to validate
     * @return true if valid, false otherwise
     */
    public static boolean hasValidDay(String dateString) {
        try {
            extractDay(dateString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Extracts the day from the given date string.
     *
     * @param dateString the date string to extract the day from
     * @return the day of the date
     */
    public static int extractDay(String dateString) {
        requireNonNull(dateString);
        String[] parts = dateString.split(" ");
        return Integer.parseInt(parts[0]);
    }


    @Override
    public int compareTo(AppointmentDate o) {
        return date.compareTo(o.date);
    }

    /**
     * Returns the appointment date as a formatted string in 'dd MMM yyyy'.
     */
    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    /**
     * Returns true if both appointment dates are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentDate)) {
            return false;
        }

        AppointmentDate otherDate = (AppointmentDate) other;
        return date.equals(otherDate.date);
    }

    /**
     * Returns the hashcode of the appointment date.
     */
    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
