package tahub.contacts.model.studentcourseassociation.exceptions;

/**
 * Signals that the operation is unable to find a/the desired
 * {@link tahub.contacts.model.studentcourseassociation.StudentCourseAssociation}.
 */
public class ScaNotFoundException extends RuntimeException {
    public ScaNotFoundException(String message) {
        super(message);
    }
}
