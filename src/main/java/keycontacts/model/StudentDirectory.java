package keycontacts.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
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
import keycontacts.model.student.Group;
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
        ArrayList<Student> uniqueGroupStudents = new ArrayList<>();
        for (Student student : students) {
            boolean groupPresent = false;
            for (Student uniqueGroupStudent: uniqueGroupStudents) {
                if (student.getGroup().isSameGroup(uniqueGroupStudent.getGroup())) {
                    groupPresent = true;
                }
            }

            if (!groupPresent) {
                uniqueGroupStudents.add(student);
            }
        }

        Stream<Lesson> clashingLessonsStream1 = uniqueGroupStudents.stream()
                .flatMap(student -> student.getMakeupLessons().stream())
                .flatMap(ml -> checkAgainstMakeupLessons(uniqueGroupStudents, ml).stream());
        Stream<Lesson> clashingLessonsStream2 = uniqueGroupStudents.stream()
                .flatMap(student -> student.getMakeupLessons().stream())
                .flatMap(ml -> checkAgainstRegularLessons(uniqueGroupStudents, ml).stream());
        Stream<Lesson> clashingLessonsStream3 = uniqueGroupStudents.stream()
                .map(student -> student.getRegularLesson())
                .flatMap(rl -> checkAgainstRegularLessons(uniqueGroupStudents, rl).stream());
        return Stream.concat(clashingLessonsStream1,
                Stream.concat(clashingLessonsStream2, clashingLessonsStream3))
                .collect(Collectors.toSet()); // duplicate clashing lessons get eliminated during set conversion
    }

    /**
     * Checks if a {@code MakeupLesson} clashes with any student's {@code MakeupLesson}s.
     * @return A {@code Set<Lesson>} of clashing lessons.
     */
    private Set<Lesson> checkAgainstMakeupLessons(ArrayList<Student> uniqueGroupStudents, MakeupLesson makeupLesson) {
        return uniqueGroupStudents.stream()
                .flatMap(student -> student.getMakeupLessons().stream())
                .filter(ml -> makeupLesson.isClashing(ml))
                .map(ml -> (Lesson) ml) // casting to lesson
                .collect(Collectors.toSet());
    }

    /**
     * Checks if a {@code RegularLesson} clashes with any student's {@code RegularLesson}.
     * @return A {@code Set<Lesson>} of clashing lessons.
     */
    private Set<Lesson> checkAgainstRegularLessons(ArrayList<Student> uniqueGroupStudents,
                                                   RegularLesson regularLesson) {
        return uniqueGroupStudents.stream()
                .map(student -> student.getRegularLesson())
                .filter(rl -> rl != null && regularLesson != null && regularLesson.isClashing(rl))
                .map(rl -> (Lesson) rl) // casting to lesson
                .collect(Collectors.toSet());
    }

    /**
     * Checks if a {@code MakeupLesson} clashes with any student's {@code RegularLesson}.
     * @return A {@code Set<Lesson>} of clashing lessons.
     */
    private Set<Lesson> checkAgainstRegularLessons(ArrayList<Student> uniqueGroupStudents, MakeupLesson makeupLesson) {
        Set<Lesson> clashingLessons = new HashSet<>();
        for (Student student : uniqueGroupStudents) {
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
     * Returns true if there are no invalid cancelled lessons.
     */
    public boolean hasInvalidCancelledLessons() {
        ObservableList<Student> students = this.getStudentList();
        return !students.stream()
                .flatMap(student -> student.getCancelledLessons().stream() // converts each student into boolean stream
                        .map(lesson -> lesson.getLessonDate().convertToDay()) // converts cl to cl lesson day
                        .map(day -> day.equals(student.getRegularLessonOptional() // returns true if cl day matches rl
                                .map(RegularLesson::getLessonDay).orElse(null))))
                .reduce(true, (a, b) -> a && b);
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

    public ArrayList<Student> getStudentsInGroup(Group targetGroup) {
        ObservableList<Student> students = this.getStudentList();
        ArrayList<Student> studentsInGroup = new ArrayList<>();
        for (Student student : students) {
            if (student.getGroup().isSameGroup(targetGroup)) {
                studentsInGroup.add(student);
            }
        }

        return studentsInGroup;
    }

    /**
     * Used by {@code JsonSerializableStudentDirectory} to check for any students in the same group with different
     * lessons.
     */
    public boolean hasGroupSyncErrors() {
        ObservableList<Student> students = this.getStudentList();
        for (Student student : students) {
            for (Student otherStudent: students) {
                // skip if it is the same student
                if (student == otherStudent) {
                    continue;
                }

                if (student.getGroup().isSameGroup(otherStudent.getGroup())) {
                    // check regular lesson
                    if (!student.getRegularLessonOptional().equals(otherStudent.getRegularLessonOptional())) {
                        return true;
                    }

                    // check cancelled lessons
                    if (!student.getCancelledLessons().equals(otherStudent.getCancelledLessons())) {
                        return true;
                    }

                    // check makeup lessons
                    if (!student.getMakeupLessons().equals(otherStudent.getMakeupLessons())) {
                        return true;
                    }
                }
            }
        }
        return false;
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

        StudentDirectory otherStudentDirectory = (StudentDirectory) other;
        return students.equals(otherStudentDirectory.students);
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
