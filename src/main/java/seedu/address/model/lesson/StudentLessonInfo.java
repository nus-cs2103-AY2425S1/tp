package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

import seedu.address.model.student.Student;

/**
 * This class encapsulates an entry pairing Student objects with lesson info fields.
 */
public class StudentLessonInfo implements Serializable {
    private final Student student;
    private final boolean attendance;
    private final int participationScore;

    /**
     * Creates a new StudentLessonInfo instance with the given parameters.
     *
     * @param student Student instance, must not be null.
     * @param attendance True if the student has attended the lesson.
     * @param participationScore An arbitrary integer describing the student's participation.
     */
    public StudentLessonInfo(Student student, boolean attendance, int participationScore) {
        requireNonNull(student);
        this.student = student;
        this.attendance = attendance;
        this.participationScore = participationScore;
    }

    /**
     * Creates a new StudentLessonInfo with the default parameters.
     * Attendance is set to false.
     * Participation is set to 0.
     *
     * @param student The student instance to create a StudentLessonInfo for.
     * @return A new StudentLessonInfo instance with default arguments.
     */
    public static StudentLessonInfo initialise(Student student) {
        return new StudentLessonInfo(student, false, 0);
    }

    /**
     * Returns true if this StudentLessonInfo instance belongs to the given student.
     * Uses weak equality checking (name equality) since students should be unique by name.
     *
     * @param student The student instance to check against.
     * @return True if this instance belongs to the given student.
     */
    public boolean isForStudent(Student student) {
        return this.student.isSameStudent(student);
    }

    /**
     * Returns the student instance.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Returns the attendance boolean of this student.
     *
     * @return True if the student has attended the lesson.
     */
    public boolean getAttendance() {
        return attendance;
    }

    /**
     * Returns the participation score of this student.
     *
     * @return An integer representing the student's participation.
     */
    public int getParticipationScore() {
        return participationScore;
    }

    /**
     * Returns a new StudentLessonInfo instance with its attendance modified to the given value.
     *
     * @param attendance True if the student has attended the lesson.
     */
    public StudentLessonInfo setAttendance(boolean attendance) {
        return new StudentLessonInfo(student, attendance, participationScore);
    }

    /**
     * Returns a new StudentLessonInfo instance with its participation modified to the given value.
     *
     * @param participationScore An integer representing the student's participation.
     */
    public StudentLessonInfo setParticipationScore(int participationScore) {
        return new StudentLessonInfo(student, attendance, participationScore);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StudentLessonInfo)) {
            return false;
        }

        StudentLessonInfo otherInfo = (StudentLessonInfo) other;
        return (student.isSameStudent(otherInfo.student))
                && (attendance == otherInfo.attendance)
                && (participationScore == otherInfo.participationScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, attendance, participationScore);
    }
}
