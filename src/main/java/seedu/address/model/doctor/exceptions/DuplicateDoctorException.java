package seedu.address.model.doctor.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDoctorException extends RuntimeException {
    public DuplicateDoctorException() {
        super("Operation would result in duplicate doctors");
    }
}
