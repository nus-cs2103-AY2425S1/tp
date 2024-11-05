package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the type of an Appointment in the appointment book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentType(String)}
 */
public class AppointmentType {

    public static final String MESSAGE_CONSTRAINTS =
            "Appointment types can take any values, and it should not be blank";

    /*
     * The first character of the appointment type must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code AppointmentType}.
     *
     * @param appointmentType A valid appointmentType.
     */
    public AppointmentType(String appointmentType) {
        requireNonNull(appointmentType);
        checkArgument(isValidAppointmentType(appointmentType), MESSAGE_CONSTRAINTS);
        value = appointmentType;
    }

    /**
     * Returns true if a given string is a valid appointment type.
     */
    public static boolean isValidAppointmentType(String test) {
        return test.matches(VALIDATION_REGEX);
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

        // instanceof handles nulls
        if (!(other instanceof AppointmentType otherAppointmentType)) {
            return false;
        }

        return value.equals(otherAppointmentType.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

