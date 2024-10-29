package seedu.address.model.person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's appointment in the address book.
 */
public class Appointment {

    /** Message constraints that indicate the valid format for appointments. */
    public static final String MESSAGE_CONSTRAINTS = "Appointments should be of the format DD-MM-YYYY HH:MM";

    /** Regex for validating appointment format. */
    public static final String VALIDATION_REGEX =
            "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4}) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";

    /** Formatter for displaying and parsing appointment date and time. */
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /** The date and time value of the appointment. */
    public final LocalDateTime value;

    /**
     * Constructs an {@code Appointment} with the specified date and time string.
     *
     * @param dateTimeString A valid appointment date string.
     */
    public Appointment(String dateTimeString) {
        if (dateTimeString == null) {
            this.value = null;
            return;
        }
        this.value = LocalDateTime.parse(dateTimeString, FORMATTER);
    }

    /**
     * Returns true if a given string is a valid appointment date according to {@code VALIDATION_REGEX}.
     *
     * @param test The string to be tested for validity as an appointment date.
     * @return {@code true} if the string is a valid appointment date, otherwise {@code false}.
     */
    public static boolean isValidAppointment(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a string representation of the appointment date and time in the specified format.
     *
     * @return The formatted appointment date and time.
     */
    @Override
    public String toString() {
        return value.format(FORMATTER);
    }

    /**
     * Checks if this appointment is equal to another appointment.
     *
     * @param other The other object to compare to.
     * @return {@code true} if the other object is an {@code Appointment} with the same date and time,
     *         otherwise {@code false}.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return (value.toString()).equals(otherAppointment.value.toString());
    }

    /**
     * Returns the hash code of the appointment.
     *
     * @return The hash code of the appointment.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns the time of the appointment as a {@code LocalDateTime} object.
     *
     * @return The appointment time.
     */
    public LocalDateTime getTime() {
        return this.value;
    }

    /**
     * Checks if the appointment is on the same date as the specified {@code LocalDate}.
     *
     * @param currentDate The date to compare with the appointment date.
     * @return {@code true} if the appointment is on the same date, otherwise {@code false}.
     */
    public boolean isSameDate(LocalDate currentDate) {
        if (value == null) {
            return false;
        }
        return value.toLocalDate().equals(currentDate);
    }
}
