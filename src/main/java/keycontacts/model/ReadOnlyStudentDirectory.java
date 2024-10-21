package keycontacts.model;

import javafx.collections.ObservableList;
import keycontacts.model.student.Student;

/**
 * Unmodifiable view of a student directory.
 */
public interface ReadOnlyStudentDirectory {

    /**
     * Returns an unmodifiable view of the students list.
     * This list will not contain any duplicate students.
     */
    ObservableList<Student> getStudentList();

}
