package seedu.address.model.appointment.exceptions;

import seedu.address.model.appointment.Appointment;

/**
 * Signals that an operation is invalid due to the appointment timing being
 * invalid.
 */
public class InvalidAppointmentException extends RuntimeException {

    public InvalidAppointmentException() {
        super(Appointment.INVALID_APPOINTMENT_ERROR);
    }

}
