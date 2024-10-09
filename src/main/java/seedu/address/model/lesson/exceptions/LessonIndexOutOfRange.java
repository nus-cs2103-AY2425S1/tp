package seedu.address.model.lesson.exceptions;

public class LessonIndexOutOfRange extends RuntimeException {
    public LessonIndexOutOfRange() {
        super("Operation would result in lesson index out of range");
    }
}
