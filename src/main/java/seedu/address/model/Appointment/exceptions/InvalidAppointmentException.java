package seedu.address.model.appointment.exceptions;

public class InvalidAppointmentException extends RuntimeException {

    public InvalidAppointmentException() {
        super("Invalid Appointment: Start time must be before end time");
    }
    
}
