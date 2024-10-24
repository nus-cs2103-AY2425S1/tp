package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.exceptions.ConsultationNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueStudentList;

/**
 * Wraps all data at the address-book level
 * Duplicate students are not allowed (by .isSameStudent comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueStudentList students;
    private final ObservableList<Consultation> consults;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
        consults = FXCollections.observableArrayList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the data in the {@code toBeCopied}
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
     * Replaces the contents of the consultation list with {@code consults}.
     */
    public void setConsults(List<Consultation> consults) {
        this.consults.setAll(consults);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
        setConsults(newData.getConsultList());
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
     * Returns true if a consult with the same details as the given consult exists in TAHub.
     * @param consult The consultation to search for.
     * @return True if a consultation is found.
     */
    public boolean hasConsult(Consultation consult) {
        requireNonNull(consult);
        return consults.contains(consult);
    }

    /**
     * Adds a student to the address book.
     * The student must not already exist in the address book.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Adds a consultation to the address book and sorts the list by date.
     */
    public void addConsult(Consultation consult) {
        consults.add(consult);

        // Sort by date first, and by time if the dates are the same
        consults.sort((c1, c2) -> {
            int dateComparison = c1.getDate().compareTo(c2.getDate());
            if (dateComparison == 0) {
                return c1.getTime().compareTo(c2.getTime()); // Compare by time if dates are the same
            }
            return dateComparison; // Otherwise, compare by date
        });
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same
     * as another existing student in the address book.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Replaces the given consultation {@code target} in the list with {@code editedConsult}.
     * {@code target} must exist in TAHub.
     */
    public void setConsult(Consultation target, Consultation editedConsult) {
        requireAllNonNull(target, editedConsult);

        int index = consults.indexOf(target);
        if (index == -1) {
            throw new ConsultationNotFoundException();
        }

        consults.set(index, editedConsult);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    /**
     * Removes {@code consult} from this {@code AddressBook}.
     * {@code consult} must exist in TAHub.
     *
     * @param consult The consult to be removed.
     */
    public void removeConsult(Consultation consult) {
        consults.remove(consult);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("students", students)
                .add("consults", consults)
                .toString();
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    public ObservableList<Consultation> getConsultList() {
        return FXCollections.unmodifiableObservableList(consults);
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
                && consults.equals(otherAddressBook.consults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(students, consults);
    }
}

