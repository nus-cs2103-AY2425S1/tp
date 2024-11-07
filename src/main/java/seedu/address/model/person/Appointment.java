package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Person's appointment in the address book.
 */
public class Appointment {

    public static final String MESSAGE_CONSTRAINTS =
            "Appointments should be in the format 'yyyy-MM-dd HH:mm', "
                    + "must be a valid date and time, and must be scheduled after today's date.";

    /*
     * The appointment date and time should in the format 'yyyy-MM-dd HH:mm'.
     * Example: 2023-01-31 13:00
     */
    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public final LocalDateTime date;

    public final String value;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param appointmentDateStr A valid date and time string.
     */
    public Appointment(String appointmentDateStr) {
        requireNonNull(appointmentDateStr);
        checkArgument(isValidAppointment(appointmentDateStr), MESSAGE_CONSTRAINTS);
        date = parseDateTime(appointmentDateStr);
        value = date.format(FORMATTER);
    }

    /**
     * Parses the given date and time string into a {@code LocalDateTime}.
     *
     * @param dateTimeStr The date and time string to parse.
     * @return A {@code LocalDateTime} object representing the date and time.
     */
    private static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, FORMATTER);
    }

    /**
     * Returns true if a given string is a valid appointment date and time.
     */
    public static boolean isValidAppointment(String test) {
        requireNonNull(test);
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            LocalDateTime appointmentDateTime = LocalDateTime.parse(test, FORMATTER);
            return appointmentDateTime.toLocalDate().isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Appointment && date.equals(((Appointment) other).date));
    }

}
