package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.UniqueConsultList;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueStudentList;

/**
 * Wraps all data at the address-book level
 * Duplicate students are not allowed (by .isSameStudent comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueStudentList students;
    private final UniqueConsultList consults; // Use UniqueConsultList instead of ObservableList
    private final UniqueLessonList lessons;

    /*
     * The 'unusual' code block below is a non-static initialization block,
     * sometimes used to avoid duplication
     * between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other
     * ways to avoid duplication
     * among constructors.
     */
    {
        students = new UniqueStudentList();
        consults = new UniqueConsultList(); // Initialize UniqueConsultList
        lessons = new UniqueLessonList();
    }


    public AddressBook() {
    }

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

    // Consultation-level operations
    /**
     * Returns true if a consultation with the same details as {@code consult} exists in TAHub.
     */
    public boolean hasConsult(Consultation consult) {
        requireNonNull(consult);
        return consults.contains(consult); // Use UniqueConsultList's contains method
    }

    /**
     * Adds a consultation to the address book.
     * The consultation must not already exist in the address book.
     */
    public void addConsult(Consultation consult) {
        requireNonNull(consult);
        consults.add(consult);
        consults.sort();
    }

    /**
     * Replaces the contents of the consultation list with {@code consults}.
     */
    public void setConsults(List<Consultation> consults) {
        this.consults.setConsults(consults); // Use setConsults method from UniqueConsultList
        this.consults.sort();
    }

    /**
     * Replaces the given consultation {@code target} in the list with {@code editedConsult}.
     * {@code target} must exist in TAHub.
     */
    public void setConsult(Consultation target, Consultation editedConsult) {
        requireAllNonNull(target, editedConsult);
        consults.setConsult(target, editedConsult); // Use setConsult method from UniqueConsultList
    }

    /**
     * Removes {@code consult} from this {@code AddressBook}.
     * {@code consult} must exist in TAHub.
     */
    public void removeConsult(Consultation consult) {
        requireNonNull(consult);
        consults.remove(consult); // Use remove method from UniqueConsultList
    }

    /**
     * Returns an unmodifiable view of the consultation list.
     */
    public ObservableList<Consultation> getConsultList() {
        return consults.asUnmodifiableObservableList(); // Use UniqueConsultList's unmodifiable list view
    }

    /**
     * Replaces the contents of the lesson list with {@code lesson}.
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons.setLessons(lessons);
        this.lessons.sort();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setStudents(newData.getStudentList());
        setConsults(newData.getConsultList());
        setLessons(newData.getLessonList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in
     * the address book.
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
     * Replaces the given student {@code target} in the list with
     * {@code editedStudent}.
     * {@code target} must exist in the address book.
     * The student identity of {@code editedStudent} must not be the same
     * as another existing student in the address book.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        // Set Student in Student List
        students.setStudent(target, editedStudent);

        // Set Student in Consultation List
        List<Consultation> consultsWithEditedStudent = consults.filtered(c -> c.hasStudent(target));
        consultsWithEditedStudent.forEach(c -> {
            Consultation newConsult = new Consultation(c);
            newConsult.setStudent(target, editedStudent);
            setConsult(c, newConsult);
        });

        // Set Student in Lesson List
        List<Lesson> lessonsWithEditedStudent = lessons.filtered(l -> l.hasStudent(target)).stream().toList();
        lessonsWithEditedStudent.forEach(l -> {
            Lesson newLesson = new Lesson(l);
            newLesson.setStudent(target, editedStudent);
            setLesson(l, newLesson);
        });
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * Also removes the student from all consultations and lessons.
     * {@code key} must exist in the address book.
     */
    public void removeStudent(Student key) {
        students.remove(key);

        // Remove from consultations
        List<Consultation> consultsWithDeletedStudent = consults.filtered(c -> c.hasStudent(key));
        consultsWithDeletedStudent.forEach(c -> {
            Consultation newConsult = new Consultation(c);
            newConsult.removeStudent(key);
            setConsult(c, newConsult);
        });

        // Remove from lessons
        List<Lesson> lessonsWithDeletedStudent = lessons.filtered(l -> l.hasStudent(key)).stream().toList();
        lessonsWithDeletedStudent.forEach(l -> {
            Lesson newLesson = new Lesson(l);
            newLesson.removeStudent(key);
            setLesson(l, newLesson);
        });
    }

    //// util methods

    @Override
    public String toString() {
        return AddressBook.class.getCanonicalName() + "{students=" + students.asUnmodifiableObservableList()
            + ", consults=" + consults.asUnmodifiableObservableList()
            + ", lessons=" + lessons.asUnmodifiableObservableList() + "}";
    }

    @Override
    public ObservableList<Student> getStudentList() {
        return students.asUnmodifiableObservableList();
    }

    /**
     * Checks if this {@code AddressBook} is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if both AddressBooks have the same students, consultations, and
     *         lessons, false otherwise.
     */
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
                && consults.equals(otherAddressBook.consults)
                && lessons.equals(otherAddressBook.lessons);
    }

    /**
     * Returns the hash code for this {@code AddressBook}.
     *
     * @return The hash code based on students, consultations, and lessons.
     */
    @Override
    public int hashCode() {
        return Objects.hash(students, consults, lessons);
    }

    /**
     * Checks if the address book contains the specified {@code Lesson}.
     *
     * @param lesson The lesson to check.
     * @return true if the lesson exists in the address book, false otherwise.
     * @throws NullPointerException if {@code lesson} is null.
     */
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return lessons.contains(lesson);
    }

    /**
     * Adds a {@code Lesson} to the address book and sorts the lesson list by date.
     * If two lessons have the same date, they are further sorted by time.
     *
     * @param lesson The lesson to add.
     * @throws NullPointerException if {@code lesson} is null.
     */
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        lessons.add(lesson);
        lessons.sort();
    }

    /**
     * Removes a {@code Lesson} from the address book.
     *
     * @param lesson The lesson to remove.
     * @throws NullPointerException if {@code lesson} is null.
     */
    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    /**
     * Returns an unmodifiable view of the lesson list.
     *
     * @return An unmodifiable {@code ObservableList} containing all lessons in the
     *         address book.
     */
    public ObservableList<Lesson> getLessonList() {
        return lessons.asUnmodifiableObservableList();
    }

    /**
     * Replaces the given lesson {@code target} in the list with
     * {@code editedLesson}.
     * {@code target} must exist in the address book.
     *
     * @param target       The lesson to be replaced.
     * @param editedLesson The new lesson to replace the target.
     * @throws NullPointerException    if {@code target} or {@code editedLesson} is
     *                                 null.
     * @throws LessonNotFoundException if {@code target} could not be found in the
     *                                 list.
     */
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireAllNonNull(target, editedLesson);
        lessons.setLesson(target, editedLesson);
    }
}
