package tahub.contacts.model.course.exceptions;

/**
 * Signals that the operation will result in duplicate Courses (Courses are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCourseException extends RuntimeException {
    public DuplicateCourseException() {
        super("Operation would result in duplicate courses");
    }
}
