package seedu.edulog.model;

import javafx.collections.ObservableList;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.student.Student;

/**
 * Unmodifiable view of an edulog book.
 */
public interface ReadOnlyEduLog {

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the lessons list.
     * This list will not contain any duplicate lessons, as validated by the call to hasLesson before addLesson.
     */
    ObservableList<Lesson> getLessonList();
}
