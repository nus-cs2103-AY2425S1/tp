package tutorease.address.model;

import javafx.collections.ObservableList;
import tutorease.address.model.lesson.Lesson;
/**
 * Unmodifiable view of a lesson schedule
 */
public interface ReadOnlyLessonSchedule {

    /**
     * Returns an unmodifiable view of the lesson schedule.
     */
    ObservableList<Lesson> getLessonList();
    /**
     * Returns the lesson at the specified index.
     *
     * @param index The index of the lesson to retrieve. Must be a valid index.
     * @return The lesson at the specified index.
     */
    Lesson getLesson(int index);

}
