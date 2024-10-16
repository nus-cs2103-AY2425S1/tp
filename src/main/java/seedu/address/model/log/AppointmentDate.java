package seedu.address.model.log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents an appointment date in the log.
 */
public class AppointmentDate {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private final LocalDate date;

    /**
     * Constructs an {@code AppointmentDate} with the specified {@code LocalDate}.
     *
     * @param date A valid LocalDate.
     */
    public AppointmentDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Constructs an {@code AppointmentDate} by parsing a date string.
     *
     * @param dateString A valid date string in the format of 'yyyy-MM-dd'.
     */
    public AppointmentDate(String dateString) {
        this.date = LocalDate.parse(dateString);
    }

    /**
     * Returns the appointment date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the appointment date as a formatted string in 'MMM dd yyyy'.
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

