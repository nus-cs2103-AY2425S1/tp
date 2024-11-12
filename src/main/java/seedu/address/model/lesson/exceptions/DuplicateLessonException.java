package seedu.address.model.lesson.exceptions;

/**
 * Signals that the operation will result in duplicate Lessons
 * (Lessons are considered duplicates if they have the same date and time).
 */
public class DuplicateLessonException extends RuntimeException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate lessons");
    }
}
