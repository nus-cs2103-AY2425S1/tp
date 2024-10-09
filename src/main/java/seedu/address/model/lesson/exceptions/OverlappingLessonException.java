package seedu.address.model.lesson.exceptions;

public class OverlappingLessonException extends RuntimeException {
    public OverlappingLessonException() {
        super("Operation would result in overlapping lessons");
    }
}
