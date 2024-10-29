package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.model.student.exceptions.StudentNotFoundException;


/**
 * Represents a Lesson in the system.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Lesson {

    private final Date date;
    private final Time time;
    private final List<StudentLessonInfo> studentLessonInfoList;

    /**
     * Constructs a {@code Lesson} with the given arguments.
     *
     * @param date The date of the lesson.
     * @param time The time of the lesson.
     */
    public Lesson(Date date, Time time) {
        requireAllNonNull(date, time);
        this.date = date;
        this.time = time;
        this.studentLessonInfoList = new ArrayList<>();
    }

    /**
     * Constructs a {@code Lesson} with the given arguments.
     *
     * @param date The date of the lesson.
     * @param time The time of the lesson.
     * @param studentLessonInfoList A list of student lesson info objects that may be empty but not null.
     * @throws NullPointerException if any of the arguments are null.
     */
    public Lesson(Date date, Time time, List<StudentLessonInfo> studentLessonInfoList) {
        requireAllNonNull(date, time, studentLessonInfoList);
        studentLessonInfoList.forEach(Objects::requireNonNull);
        this.date = date;
        this.time = time;
        this.studentLessonInfoList = new ArrayList<>(studentLessonInfoList);
    }

    /**
     * Constructs a copy of the given lesson.
     * Creates new instances of date, time, and the studentLessonInfo list (not the entries themselves)
     * to reduce the risk of accidental mutation.
     *
     * @param lesson The lesson to copy.
     */
    public Lesson(Lesson lesson) {
        requireNonNull(lesson);
        this.date = new Date(lesson.getDate().getValue());
        this.time = new Time(lesson.getTime().getValue());
        this.studentLessonInfoList = new ArrayList<>(lesson.getStudentLessonInfoList());
    }

    /**
     * Returns the date of the lesson.
     *
     * @return The date of the lesson.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the time of the lesson.
     *
     * @return The time of the lesson.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns an immutable list of students attending the lesson.
     *
     * @return A list of students attending the lesson.
     */
    public List<Student> getStudents() {
        // IntelliJ says wrapping with Collections.unmodifiableList is redundant
        return studentLessonInfoList.stream().map(StudentLessonInfo::getStudent).toList();
    }

    /**
     * Returns an immutable list of students attending the lesson.
     *
     * @return A list of students attending the lesson.
     */
    public List<StudentLessonInfo> getStudentLessonInfoList() {
        return Collections.unmodifiableList(studentLessonInfoList);
    }

    /**
     * Returns true if the lesson contains the specified student.
     *
     * @param student The student to check for.
     * @return True if the student is attending the lesson, false otherwise.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return studentLessonInfoList.stream()
                .anyMatch(studentLessonInfo -> studentLessonInfo.isForStudent(student));
    }

    /**
     * Helper method to get the StudentLessonInfo wrapper object for the given student.
     *
     * @param student The student to find with.
     * @return The StudentLessonInfo object associated with the given student.
     * @throws StudentNotFoundException if the student is not in this lesson.
     */
    private StudentLessonInfo getStudentLessonInfo(Student student) throws StudentNotFoundException {
        return studentLessonInfoList.stream()
                .filter(studentLessonInfo -> studentLessonInfo.isForStudent(student))
                .findAny().orElseThrow(StudentNotFoundException::new);
    }

    /**
     * Adds a student to the lesson.
     *
     * @param student The student to add.
     */
    public void addStudent(Student student) {
        requireNonNull(student);
        if (hasStudent(student)) {
            throw new DuplicateStudentException();
        }
        studentLessonInfoList.add(StudentLessonInfo.initialise(student));
    }

    /**
     * Removes a student from the lesson. If the student is not present, do nothing.
     *
     * @param student The student to remove.
     */
    public void removeStudent(Student student) {
        requireNonNull(student);
        studentLessonInfoList.removeIf(studentLessonInfo -> studentLessonInfo.isForStudent(student));
    }

    /**
     * Sets the attendance of the student to the specified value.
     *
     * @param student The student to mark.
     * @param attendance True if the student is attending this lesson, false otherwise.
     * @throws StudentNotFoundException if the student is not in the attendance map.
     */
    public void setAttendance(Student student, boolean attendance) throws StudentNotFoundException {
        requireNonNull(student);
        StudentLessonInfo oldEntry = getStudentLessonInfo(student);
        studentLessonInfoList.remove(oldEntry);
        StudentLessonInfo newEntry = oldEntry.setAttendance(attendance);
        studentLessonInfoList.add(newEntry);
    }

    /**
     * Returns true if this student is marked as having attended this lesson.
     *
     * @param student The student to check.
     * @return True if the student is marked as having attended the lesson.
     */
    public boolean getAttendance(Student student) throws StudentNotFoundException {
        return getStudentLessonInfo(student).getAttendance();
    }

    /**
     * Sets the participation of the student to the specified value.
     *
     * @param student The student to mark.
     * @param participationScore Number representing the student's participation.
     * @throws StudentNotFoundException if the student is not in the lesson.
     */
    public void setParticipation(Student student, int participationScore) throws StudentNotFoundException {
        requireNonNull(student);
        StudentLessonInfo oldEntry = getStudentLessonInfo(student);
        studentLessonInfoList.remove(oldEntry);
        StudentLessonInfo newEntry = oldEntry.setParticipationScore(participationScore);
        studentLessonInfoList.add(newEntry);
    }

    /**
     * Returns the number representing the student's participation for this lesson.
     *
     * @param student The student to check.
     * @return Number representing the student's participation.
     * @throws StudentNotFoundException if the student is not in the lesson.
     */
    public int getParticipation(Student student) throws StudentNotFoundException {
        return getStudentLessonInfo(student).getParticipationScore();
    }

    /**
     * Returns true if both lessons have the same date, time, and students.
     *
     * @param other The other lesson to compare.
     * @return True if both lessons are the same, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return date.equals(otherLesson.date)
                && time.equals(otherLesson.time)
                && studentLessonInfoList.equals(otherLesson.studentLessonInfoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, studentLessonInfoList);
    }

    @Override
    public String toString() {
        return String.format("Lesson[date=%s, time=%s, studentLessonInfoList=%s]",
                date, time, studentLessonInfoList);
    }
}
