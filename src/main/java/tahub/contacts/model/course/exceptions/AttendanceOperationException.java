package tahub.contacts.model.course.exceptions;

/**
 * Signals that there is an error with an {@link tahub.contacts.model.course.Attendance} operation.
 */
public class AttendanceOperationException extends Exception {
    public AttendanceOperationException(String message) {
        super(message);
    }
}
