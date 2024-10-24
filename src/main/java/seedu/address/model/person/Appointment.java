package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person's appointment in the address book.
 */
public class Appointment {

    public static final String MESSAGE_CONSTRAINTS = "Appointments should be of the format DD-MM-YYYY HH:MM";
    public static final String VALIDATION_REGEX =
            "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4}) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";
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

    /**
     * Returns true if a given string is a valid appointment date.
     */
    public static boolean isValidAppointment(String test) {
        return test.matches(VALIDATION_REGEX);
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
