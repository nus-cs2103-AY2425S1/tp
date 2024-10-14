package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialClass;
import seedu.address.model.student.UniqueStudentList;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.model.tut.Tut;
import seedu.address.model.tut.exceptions.TutNoFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueStudentList students;

    private final List<Tut> tutorials;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        tutorials = new ArrayList<>();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Students in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the student list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        this.students.setStudents(students);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in the address book.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the address book.
     * The student must not already exist in the address book.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same as another
     * existing student in the address book.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }



    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("students", students)
                .toString();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return students.equals(otherAddressBook.students)
                && tutorials.equals(otherAddressBook.tutorials);
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }

    public void addTutorial(Tut tutorial) {
        this.tutorials.add(tutorial);
    }

    public List<Tut> getTutorials() {
        return this.tutorials;
    }

    /**
     * Checks if tutorial exists in book
     * @param tutorial
     * @return boolean representing whether tutorial exists in book
     */
    public boolean hasTutorial(Tut tutorial) {
        requireNonNull(tutorial);
        return tutorials.contains(tutorial);
    }

    /**
     * Assigns student to tutorial
     *
     */
    public void assignStudent(Student key, Tut tutKey) {
        if (!hasStudent(key)) {
            throw new StudentNotFoundException();
        }
        if (!hasTutorial(tutKey)) {
            throw new TutNoFoundException();
        }
        tutKey.add(key);
    }

    /**
     * Removes {@code tutorial} from this {@code AddressBook}.
     * {@code tutorial} must exist in the address book.
     *
     * @param tutorialClass The tutorial class to be deleted.
     */
    public void deleteTutorial(TutorialClass tutorialClass) {
        requireNonNull(tutorialClass);
        this.tutorials.removeIf(id -> id.getTutorialClass().equals(tutorialClass));
    }

    /**
     * Check if tutorial class exist in tutorial list.
     *
     * @param tutorialClass The tutorial class to be checked.
     * @return True if tutorial class exist.
     */
    public boolean hasTutorialClass(TutorialClass tutorialClass) {
        requireNonNull(tutorialClass);
        return tutorials.stream().anyMatch(x -> x.getTutorialClass().equals(tutorialClass));
    }
}
