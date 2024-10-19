package seedu.address.model.consultation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.model.student.Student;

/**
 * Represents a Consultation in the system.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Consultation {

    // Identity fields
    private final Date date;
    private final Time time;

    // Data field
    private final List<Student> students;

    /**
     * Every field must be present and not null.
     * The list of students can be empty but not null.
     */
    public Consultation(Date date, Time time, List<Student> students) {
        requireAllNonNull(date, time);
        this.date = date;
        this.time = time;

        this.students = students != null ? new ArrayList<>(students) : new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    /**
     * Returns an immutable list of students, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    /**
     * Adds a student to the consultation.
     */
    public void addStudent(Student student) {
        students.add(student);
    }

    /**
     * Removes a student from the consultation.
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    /**
     * Returns true if both consultations have the same date, time, and students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Consultation)) {
            return false;
        }

        Consultation otherConsultation = (Consultation) other;
        return date.equals(otherConsultation.date)
                && time.equals(otherConsultation.time)
                && students.equals(otherConsultation.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, students);
    }

    @Override
    public String toString() {
        return String.format("Consultation[date=%s, time=%s, students=%s]", date, time, students);
    }

    /**
     * Ensures that none of the arguments passed to the constructor are null, except the student list can be empty.
     */
    private void requireAllNonNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                throw new NullPointerException("Fields date and time must be non-null");
            }
        }
    }
}
