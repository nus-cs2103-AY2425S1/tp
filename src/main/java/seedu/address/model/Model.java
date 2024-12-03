package seedu.address.model;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;

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


    //=========== Student ================================================================================


    Student getStudentByName(Name name);

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    boolean hasStudent(Student student);

    /**
     * Deletes the given student.
     * The student must exist in the address book.
     */
    int deleteStudent(Student target);

    /**
     * Deletes all students in the list.
     */
    ObservableList<Student> deleteAllStudents();

    /**
     * Get the current filter for the student list
     * @return {@code Predicate<Student>} for the student list
     */
    Predicate<Student> getPredicate();

    /**
     * Adds the given student.
     * {@code student} must not already exist in the address book.
     */
    void addStudent(Student student);

    /**
     * Adds the given student at the given index.
     * {@code student} must not already exist in the address book.
     */
    void addStudent(int index, Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another
     * existing student in the address book.
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
     * Returns a list of students in the given tutorial group.
     */
    List<Student> getStudentsByTutorialGroup(TutorialGroup tutorialGroup);

    /**
     * Returns a list of students with the given name.
     */
    ObservableList<Student> getAllStudentsByName(Name name);

    /**
     * Replaces the current student list with the given list.
     */
    void replaceStudentList(ObservableList<Student> studentList);

}
