package keycontacts.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import keycontacts.commons.core.GuiSettings;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.student.Group;
import keycontacts.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /**
     * Reverts the student directory to the state before the latest command that edits it
     */
    void undoStudentDirectory();

    /**
     * Reverts the latest undo command if no other command has modified the student directory
     */
    void redoStudentDirectory();

    /**
     * Commits the current state of the student directory
     */
    void commitStudentDirectory();

    /**
     * Returns true if there is a state to undo to
     */
    boolean canUndoStudentDirectory();

    /**
     * Returns true if there is a state to redo to
     */
    boolean canRedoStudentDirectory();

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
     * Returns a {@Code ClashResult} object containing details of lesson clashes.
     */
    Set<Lesson> getClashingLessons();

    /**
     * Gets all students in the student directory who are part of {@code targetGroup}
     */
    ArrayList<Student> getStudentsInGroup(Group targetGroup);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getStudentList();

    /**
     * Returns an unmodifiable view of the students list, unaffected by any search filters.
     */
    ObservableList<Student> getUnfilteredStudentList();

    void filterStudentList(Predicate<Student> predicate);

    /**
     * Updates the sorted student list to sort by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortStudentList(Comparator<Student> comparator);
}
