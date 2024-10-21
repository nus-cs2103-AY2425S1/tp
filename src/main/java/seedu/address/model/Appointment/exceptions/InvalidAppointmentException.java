package seedu.address.model.appointment.exceptions;

/**
 * Signals that an operation is invalid due to the appointment timing being
 * invalid.
 */
public class InvalidAppointmentException extends RuntimeException {

    public InvalidAppointmentException() {
        super("Invalid Appointment: Start time must be before end time");
    }

}
