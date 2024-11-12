package seedu.edulog.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.edulog.commons.core.GuiSettings;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.calendar.EdulogCalendar;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.student.Student;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_PAID_STUDENTS = student -> student.getHasPaid();

    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_UNPAID_STUDENTS = student -> !student.getHasPaid();

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
     * Returns the user prefs' address book file path.
     */
    Path getEduLogFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setEduLogFilePath(Path eduLogFilePath);

    /**
     * Replaces address book data with the data in {@code eduLog}.
     */
    void setEduLog(ReadOnlyEduLog eduLog);

    /** Returns the EduLog */
    ReadOnlyEduLog getEduLog();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student.
     * The student must exist in the address book.
     */
    void deleteStudent(Student target);

    /**
     * Marks the given student as paid.
     * The student must exist in the address book.
     */
    void markStudent(Student target);

    /**
     * Mark all students
     */
    void markAllStudents();

    /**
     * Marks the given student as unpaid.
     * The student must exist in the address book.
     */
    void unmarkStudent(Student target);

    /**
     * Unmark all students
     */
    void unmarkAllStudents();

    /**
     * Get total revenue
     */
    int getPaid();

    /**
     * Get total amount of money that is unpaid
     */
    int getUnpaid();

    /**
     * Adds the given student.
     * {@code student} must not already exist in the address book.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another existing student in EduLog.
     */
    void setStudent(Student target, Student editedStudent);

    /**
     * Returns true if a lesson with the same description as {@code lesson} exists in the calendar.
     */
    boolean hasLesson(Lesson lesson);

    /**
     * Returns the lesson with the same description, if exists in the calendar (otherwise, null).
     */
    Lesson findLesson(Description description);

    /**
     * Adds the given lesson.
     * {@code lesson} must not already exist in the calendar.
     */
    void addLesson(Lesson lesson);

    /**
     * Removes the given lesson.
     * {@code lesson} must already exist in the calendar.
     */
    void removeLesson(Lesson lesson);

    /**
     * Returns edulog calendar.
     */
    EdulogCalendar getEdulogCalendar();

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Lesson> getLessonList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);
}
