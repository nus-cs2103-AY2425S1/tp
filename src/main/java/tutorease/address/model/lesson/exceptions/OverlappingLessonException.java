package tutorease.address.model.lesson.exceptions;

/**
 * Signals that the operation will result in overlapping lessons.
 */
public class OverlappingLessonException extends RuntimeException {
    public OverlappingLessonException() {
        super("Operation would result in overlapping lessons");
    }
}
