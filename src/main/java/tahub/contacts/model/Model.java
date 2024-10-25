package tahub.contacts.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import tahub.contacts.commons.core.GuiSettings;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.model.tutorial.Tutorial;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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

    Path getCourseListFilePath();

    Path getScaListFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    void setCourseListFilePath(Path courseListFilePath);

    void setScaListFilePath(Path scaListFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    void setCourseList(UniqueCourseList courseList);

    void addSca(StudentCourseAssociation sca);

    UniqueCourseList getCourseList();

    StudentCourseAssociationList getScaList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    void deleteSca(StudentCourseAssociation target);

    void addCourse(Course course);

    /**
     * Replaces the given course {@code target} with {@code editedCourse}.
     * @param target the course to be replaced
     * @param editedCourse the course to replace the target
     */
    void setCourse(Course target, Course editedCourse);

    boolean hasSca(StudentCourseAssociation sca);

    void deleteCourse(Course course);
    boolean hasCourse(Course course);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns the SCA list that contains the given student.
     *
     * @param student the student to get the SCA list for
     * @return the SCA list that contains the given student
     */
    StudentCourseAssociationList getStudentScas(Person student);

    /**
     * Returns the course list taken by a given student.
     *
     * @param student the student to get the course list for
     * @return the course list that contains the given student
     */
    UniqueCourseList getStudentCourses(Person student);

    /**
     * Returns the tutorial list taken by a given student.
     *
     * @param student the student to get the tutorial list for
     * @return the tutorial list that contains the given student
     */
    List<Tutorial> getStudentTutorials(Person student);
}
