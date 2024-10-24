package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; date is valid as declared in {@link #isValidAppointment(String)}
 */
public class Appointment {

    public static final String MESSAGE_CONSTRAINTS = "Appointments should be form dd/MM/yyyy HHmm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    public final LocalDateTime appointment;

    /**
     * Constructs a {@code Appointment}.
     *
     * @param appointment A valid date.
     */
    public Appointment(String appointment) {
        requireNonNull(appointment);
        checkArgument(isValidAppointment(appointment), MESSAGE_CONSTRAINTS);
        this.appointment = LocalDateTime.parse(appointment, FORMATTER);
    }

    public LocalDate getAppointmentDate() {
        return appointment.toLocalDate();
    }

    /**
     * Returns true if a given string is a valid appointment name.
     */
    public static boolean isValidAppointment(String test) {
        try {
            LocalDateTime.parse(test, FORMATTER);
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

        // instanceof handles nulls
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return appointment.equals(otherAppointment.appointment);
    }

    @Override
    public int hashCode() {
        return appointment.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + appointment.format(FORMATTER) + ']';
    }

}
