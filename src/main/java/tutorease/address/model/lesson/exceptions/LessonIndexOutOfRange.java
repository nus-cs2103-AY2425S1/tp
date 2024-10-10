package tutorease.address.model.lesson.exceptions;

/**
 * Signals that the operation will result in lesson index out of range.
 */
public class LessonIndexOutOfRange extends RuntimeException {
    public LessonIndexOutOfRange() {
        super("Operation would result in lesson index out of range");
    }
}
