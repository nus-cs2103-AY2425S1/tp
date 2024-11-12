package tutorease.address.model.lesson.exceptions;

/**
 * Signals that the operation will access a lesson that does not exist
 */
public class LessonNotInList extends RuntimeException {
    public LessonNotInList() {
        super("Lesson does not exist in the schedule");
    }
}
