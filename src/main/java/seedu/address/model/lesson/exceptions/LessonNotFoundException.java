package seedu.address.model.lesson.exceptions;

/**
 * Signals that the specified lesson could not be found in the list.
 */
public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException() {
        super("Lesson not found in the list");
    }
}
