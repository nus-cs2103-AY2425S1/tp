package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private final List<Student> students;
    private final Map<Student, Boolean> attendanceMap;

    /**
     * Constructs a {@code Lesson} with the given arguments.
     * The students in the student list must be present in the attendanceMap.
     * If the attendanceMap contains extra students not present in the list, they are ignored.
     *
     * @param date The date of the lesson.
     * @param time The time of the lesson.
     * @param students A list of students attending the lesson. The list may be empty, but not null.
     * @param attendanceMap A map of students to their attendance, represented by a boolean.
     */
    public Lesson(Date date, Time time, List<Student> students, Map<Student, Boolean> attendanceMap) {
        requireAllNonNull(date, time, students, attendanceMap);
        this.date = date;
        this.time = time;
        this.students = new ArrayList<>();
        this.attendanceMap = new HashMap<>();

        for (Student student : students) {
            assert attendanceMap.containsKey(student); // students in list must exist in attendanceMap
            this.students.add(student);
            this.attendanceMap.put(student, attendanceMap.get(student));
        }
    }

    /**
     * Constructs a copy of the given lesson.
     * Creates new instances of date, time, and the student list (not the students)
     * to
     * reduce the risk of accidental mutation.
     *
     * @param lesson The lesson to copy.
     */
    public Lesson(Lesson lesson) {
        requireNonNull(lesson);
        this.date = new Date(lesson.getDate().getValue());
        this.time = new Time(lesson.getTime().getValue());
        this.students = new ArrayList<>(lesson.getStudents());
        this.attendanceMap = new HashMap<>(lesson.getAttendanceMap());
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
        return Collections.unmodifiableList(students);
    }

    /**
     * Returns an immutable map of students to their attendance, represented by a boolean.
     *
     * @return A map of students to their attendance.
     */
    public Map<Student, Boolean> getAttendanceMap() {
        return Collections.unmodifiableMap(attendanceMap);
    }

    /**
     * Returns true if the lesson contains the specified student.
     *
     * @param student The student to check for.
     * @return True if the student is attending the lesson, false otherwise.
     */
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a student to the lesson.
     *
     * @param student The student to add.
     */
    public void addStudent(Student student) {
        if (hasStudent(student)) {
            throw new DuplicateStudentException();
        }
        students.add(student);
        this.attendanceMap.put(student, false); // also set default attendance as false
    }

    /**
     * Removes a student from the lesson.
     *
     * @param student The student to remove.
     */
    public void removeStudent(Student student) {
        students.remove(student);
        this.attendanceMap.remove(student);
    }

    /**
     * Sets the attendance of the student to the specified value.
     *
     * @param student The student to mark.
     * @param isAttending True if the student is attending this lesson, false otherwise.
     * @throws StudentNotFoundException if the student is not in the attendance map.
     */
    public void setAttendance(Student student, boolean isAttending) throws StudentNotFoundException {
        if (!this.attendanceMap.containsKey(student)) {
            throw new StudentNotFoundException();
        }
        this.attendanceMap.put(student, isAttending);
    }

    /**
     * Returns true if this student is marked as having attended this lesson.
     *
     * @param student The student to check.
     * @return True if the student is marked as having attended the lesson.
     */
    public boolean getAttendance(Student student) throws StudentNotFoundException {
        if (!this.attendanceMap.containsKey(student)) {
            throw new StudentNotFoundException();
        }
        return this.attendanceMap.get(student);
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
                && students.equals(otherLesson.students)
                && attendanceMap.equals(otherLesson.attendanceMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, students, attendanceMap);
    }

    @Override
    public String toString() {
        return String.format("Lesson[date=%s, time=%s, students=%s, attendanceMap=%s]",
                date, time, students, attendanceMap);
    }
}
