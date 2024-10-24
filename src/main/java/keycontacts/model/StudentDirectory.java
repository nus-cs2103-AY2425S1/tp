package keycontacts.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import keycontacts.commons.util.ToStringBuilder;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.student.Student;
import keycontacts.model.student.UniqueStudentList;

/**
 * Wraps all data at the student directory level.
 * Duplicates are not allowed (by .isSameStudent comparison)
 */
public class StudentDirectory implements ReadOnlyStudentDirectory {

    private final UniqueStudentList students;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniqueStudentList();
    }

    public StudentDirectory() {}

    /**
     * Creates a StudentDirectory using the Students in the {@code toBeCopied}
     */
    public StudentDirectory(ReadOnlyStudentDirectory toBeCopied) {
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
     * Resets the existing data of this {@code StudentDirectory} with {@code newData}.
     */
    public void resetData(ReadOnlyStudentDirectory newData) {
        requireNonNull(newData);

        setStudents(newData.getStudentList());
    }

    //// student-level operations

    /**
     * Returns true if a student with the same identity as {@code student} exists in the student directory.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the student directory.
     * The student must not already exist in the student directory.
     */
    public void addStudent(Student p) {
        students.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the student directory.
     * The student identity of {@code editedStudent} must not be the same as another
     * existing student in the student directory.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireNonNull(editedStudent);

        students.setStudent(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code StudentDirectory}.
     * {@code key} must exist in the student directory.
     */
    public void removeStudent(Student key) {
        students.remove(key);
    }

    /**
     * Used by {@code JsonSerializableStudentDirectory} to check for any clashing lessons throughout
     * the student directory.
     */
    public boolean hasClashingLessons() {
        return !getClashingLessons().isEmpty();
    }

    /**
     * Returns a set of every clashing lesson in the {@code StudentDirectory}.
     */
    public Set<Lesson> getClashingLessons() {
        ObservableList<Student> students = this.getStudentList();
        Stream<Lesson> clashingLessonsStream1 = students.stream()
                .flatMap(student -> student.getMakeupLessons().stream())
                .flatMap(ml -> checkAgainstMakeupLessons(ml).stream());
        Stream<Lesson> clashingLessonsStream2 = students.stream()
                .flatMap(student -> student.getMakeupLessons().stream())
                .flatMap(ml -> checkAgainstRegularLessons(ml).stream());
        Stream<Lesson> clashingLessonsStream3 = students.stream()
                .map(student -> student.getRegularLesson())
                .flatMap(rl -> checkAgainstRegularLessons(rl).stream());
        return Stream.concat(clashingLessonsStream1,
                Stream.concat(clashingLessonsStream2, clashingLessonsStream3))
                .collect(Collectors.toSet()); // duplicate clashing lessons get eliminated during set conversion
    }

    /**
     * Checks if a {@code MakeupLesson} clashes with any student's {@code MakeupLesson}s.
     * @return A {@code Set<Lesson>} of clashing lessons.
     */
    private Set<Lesson> checkAgainstMakeupLessons(MakeupLesson makeupLesson) {
        return this.getStudentList().stream()
                .flatMap(student -> student.getMakeupLessons().stream())
                .filter(ml -> makeupLesson.isClashing(ml))
                .map(ml -> (Lesson) ml) // casting to lesson
                .collect(Collectors.toSet());
    }

    /**
     * Checks if a {@code RegularLesson} clashes with any student's {@code RegularLesson}.
     * @return A {@code Set<Lesson>} of clashing lessons.
     */
    private Set<Lesson> checkAgainstRegularLessons(RegularLesson regularLesson) {
        return this.getStudentList().stream()
                .map(student -> student.getRegularLesson())
                .filter(rl -> rl != null && regularLesson != null && regularLesson.isClashing(rl))
                .map(rl -> (Lesson) rl) // casting to lesson
                .collect(Collectors.toSet());
    }

    /**
     * Checks if a {@code MakeupLesson} clashes with any student's {@code RegularLesson}.
     * @return A {@code Set<Lesson>} of clashing lessons.
     */
    private Set<Lesson> checkAgainstRegularLessons(MakeupLesson makeupLesson) {
        ObservableList<Student> students = this.getStudentList();
        Set<Lesson> clashingLessons = new HashSet<>();
        for (Student student : students) {
            RegularLesson regularLesson = student.getRegularLesson();
            if (regularLesson != null
                    && makeupLesson.isClashing(regularLesson)
                    && findCancelledLessonMatchingDay(student, makeupLesson).isEmpty()) {
                clashingLessons.add(regularLesson);
                clashingLessons.add(makeupLesson);
            }
        }

        return clashingLessons;
    }

    /**
     * Helper function to check if the student has a {@code CancelledLesson} on the day of the {@code MakeupLesson}
     * to see if it should ignore the student's {@code RegularLesson}.
     */
    private Optional<CancelledLesson> findCancelledLessonMatchingDay(Student student, MakeupLesson makeupLesson) {
        return student.getCancelledLessons().stream()
                .filter(cl -> makeupLesson.getLessonDate().equals(cl.getLessonDate()))
                .findFirst();
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
        if (!(other instanceof StudentDirectory)) {
            return false;
        }

        StudentDirectory otherAddressBook = (StudentDirectory) other;
        return students.equals(otherAddressBook.students);
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
