package seedu.edulog.model.calendar.exceptions;

/**
 * Signals that the operation will result in duplicate Lessons
 * (Lessons are considered duplicates by the .isSameLesson(Lesson) method checker).
 */
public class DuplicateLessonException extends RuntimeException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate lessons");
    }
}
