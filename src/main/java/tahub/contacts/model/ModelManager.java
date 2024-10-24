package tahub.contacts.model;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tahub.contacts.commons.core.GuiSettings;
import tahub.contacts.commons.core.LogsCenter;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociationList;
import tahub.contacts.model.tutorial.Tutorial;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final StudentCourseAssociationList scaList;
    private final UserPrefs userPrefs;
    private final UniqueCourseList courseList;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyUserPrefs userPrefs,
                        UniqueCourseList courseList,
                        StudentCourseAssociationList scaList) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.courseList = courseList;
        this.scaList = scaList;
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new UniqueCourseList(), new StudentCourseAssociationList());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== Course ==================================================================================

    @Override
    public Path getCourseListFilePath() {
        return userPrefs.getCourseListFilePath();
    }

    @Override
    public void setCourseListFilePath(Path courseListFilePath) {
        requireNonNull(courseListFilePath);
        userPrefs.setCourseListFilePath(courseListFilePath);
    }

    @Override
    public boolean hasCourse(Course course) {
        requireNonNull(course);
        return courseList.hasCourse(course);
    }

    @Override
    public void deleteCourse(Course target) {
        courseList.remove(target);
    }

    @Override
    public void addCourse(Course course) {
        courseList.addCourse(course);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setCourse(Course target, Course editedCourse) {
        requireAllNonNull(target, editedCourse);

        courseList.setCourse(target, editedCourse);
    }

    @Override
    public UniqueCourseList getCourseList() {
        return courseList;
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setCourseList(UniqueCourseList courseList) {
        this.courseList.setCourses(courseList);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Student Course Association List Accessors ==========================================================

    @Override
    public Path getScaListFilePath() {
        return userPrefs.getCourseListFilePath();
    }

    @Override
    public void setScaListFilePath(Path courseListFilePath) {
        requireNonNull(courseListFilePath);
        userPrefs.setCourseListFilePath(courseListFilePath);
    }

    @Override
    public boolean hasSca(StudentCourseAssociation sca) {
        requireNonNull(sca);
        return scaList.has(sca);
    }

    @Override
    public void deleteSca(StudentCourseAssociation target) {
        scaList.remove(target);
    }

    @Override
    public void addSca(StudentCourseAssociation sca) {
        scaList.add(sca);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public StudentCourseAssociationList getScaList() {
        return scaList;
    }

    @Override
    public StudentCourseAssociationList getStudentScas(Person student) {
        return scaList.filterScasByStudent(student);
    }

    @Override
    public UniqueCourseList getStudentCourses(Person student) {
        return scaList.filterCoursesByStudent(student);
    }

    @Override
    public List<Tutorial> getStudentTutorials(Person student) {
        return scaList.filterTutorialsByStudent(student);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
