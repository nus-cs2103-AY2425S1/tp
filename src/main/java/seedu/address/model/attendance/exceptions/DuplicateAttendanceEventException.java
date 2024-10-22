package seedu.address.model.attendance.exceptions;

/**
 * Signals that the operation will result in duplicate AttendanceEvents (AttendanceEvents are considered duplicates if
 * they have the same identity).
 */
public class DuplicateAttendanceEventException extends RuntimeException {
    public DuplicateAttendanceEventException() {
        super("Operation would result in duplicate attendance events");
    }
}
