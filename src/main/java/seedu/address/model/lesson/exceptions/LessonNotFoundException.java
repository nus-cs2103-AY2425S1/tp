package seedu.address.model.lesson.exceptions;

/**
 * Signals that the operation is unable to find the specified lesson.
 */
public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException() {
        super("Lesson not found in the address book.");
    }
}
