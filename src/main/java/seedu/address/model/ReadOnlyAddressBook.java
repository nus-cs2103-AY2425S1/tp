package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the consults list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Consultation> getConsultList();

    /**
     * Returns an unmodifiable view of the lessons list.
     * This list will not contain any duplicate lessons.
     */
    ObservableList<Lesson> getLessonList();
}
