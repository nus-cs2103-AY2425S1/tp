package keycontacts.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import keycontacts.commons.core.GuiSettings;
import keycontacts.model.student.Group;
import keycontacts.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' student directory file path.
     */
    Path getStudentDirectoryFilePath();

    /**
     * Sets the user prefs' student directory file path.
     */
    void setStudentDirectoryFilePath(Path studentDirectoryFilePath);

    /**
     * Replaces student directory data with the data in {@code studentDirectory}.
     */
    void setStudentDirectory(ReadOnlyStudentDirectory studentDirectory);

    /** Returns the StudentDirectory */
    ReadOnlyStudentDirectory getStudentDirectory();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the student directory.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student.
     * The student must exist in the student directory.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the student directory.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the student directory.
     * The student identity of {@code editedStudent} must not be the same
     * as another existing student in the directory.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Gets all students in the student directory who are part of {@code targetGroup}
     */
    ArrayList<Student> getStudentsInGroup(Group targetGroup);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);
}
