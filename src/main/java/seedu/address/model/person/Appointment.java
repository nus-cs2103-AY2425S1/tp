package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's appointment in the address book.
 */
public class Appointment {

    public static final String MESSAGE_CONSTRAINTS = "Appointments should be of the format DD-MM-YYYY HH:MM";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public final LocalDateTime value;

    /**
     * Constructs an {@code Appointment}.
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

    @Override
    public String toString() {
        return value.format(FORMATTER);
    }

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

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
