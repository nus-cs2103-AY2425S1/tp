package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.student.Days;
import seedu.address.model.student.Student;
import seedu.address.model.student.predicates.AttributeContainsKeywordsPredicate;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    boolean hasStudent(Student student);

    /**
     * Checks for number of students with schedule clashes with {@code student}.
     * @param student The student whose schedule is to be checked against others for clashes.
     * @return Number of students with schedule clashes.
     */
    public long checkClashes(Student student);

    /**
     * Retrieves a list of students with whom {@code student} has schedule clashes.
     * @param student The student whose schedule is to be checked against others for clashes.
     * @return List of students who have schedule conflicts with the given student.
     */
    public List<Student> getClashingStudents(Student student);

    /**
     * Retrieves a list of students with whom {@code student} has lessons on the day.
     * @param day The day to check for.
     * @return List of students scheduled to have lesson on that day.
     */
    public List<Student> getScheduledStudents(Days day);

    /**
     * Deletes the given student.
     * The student must exist in the address book.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in the address book.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another existing student
     * in the address book.
     */
    void setStudent(Student target, Student editedStudent);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);

    /**
     * Updates the filter of the filtered student list to filter by the given {@code List} of {@code predicate}.
     * @throws NullPointerException if {@code List} is null.
     */
    void updateFilteredStudentList(List<AttributeContainsKeywordsPredicate<?>> predicate);
}
