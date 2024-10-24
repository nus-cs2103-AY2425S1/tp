package seedu.address.model.log;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents an appointment date in the log.
 */
public class AppointmentDate {
    public static final String MESSAGE_CONSTRAINTS = "Invalid date format! Please use 'dd MMM yyyy'.";
    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd MMM yyyy")
            .toFormatter(Locale.ENGLISH);

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
        checkArgument(isValidDateString(dateString), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(dateString, FORMATTER);
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
