package seedu.address.model.consultation;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;

/**
 * Represents a Consultation in the system.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Consultation {

    private final Date date;
    private final Time time;
    private final List<Student> students;

    /**
     * Constructs a {@code Consultation}.
     *
     * @param date The date of the consultation.
     * @param time The time of the consultation.
     * @param students A list of students attending the consultation.
     *                 This list can be empty but must not be null.
     * @throws NullPointerException if {@code date} or {@code time} is null.
     */
    public Consultation(Date date, Time time, List<Student> students) {
        requireAllNonNull(date, time);
        this.date = date;
        this.time = time;

        this.students = students != null ? new ArrayList<>(students) : new ArrayList<>();
    }

    /**
     * Constructs a copy of the given consultation.
     * Creates new instances of date, time and the student list (not the students) to
     * reduce the risk of accidental mutation.
     *
     * @param consultation The consultation to copy.
     */
    public Consultation(Consultation consultation) {
        requireNonNull(consultation);
        this.date = new Date(consultation.getDate().getValue());
        this.time = new Time(consultation.getTime().getValue());
        this.students = new ArrayList<>(consultation.getStudents());
    }

    /**
     * Returns the date of the consultation.
     *
     * @return The date of the consultation.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the time of the consultation.
     *
     * @return The time of the consultation.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns an immutable list of students attending the consultation.
     *
     * @return A list of students attending the consultation.
     * @throws UnsupportedOperationException if an attempt is made to modify the returned list.
     */
    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    /**
     * Adds a student to the consultation.
     *
     * @param student The student to add.
     */
    public void addStudent(Student student) {
        if (hasStudent(student)) {
            throw new DuplicateStudentException();
        }
        students.add(student);
    }

    /**
     * Removes a student from the consultation.
     *
     * @param student The student to remove.
     */
    public void removeStudent(Student student) {
        students.remove(student);
    }

    /**
     * Returns true if the consultation contains the specified student.
     *
     * @param student The student to check for.
     * @return True if the student is attending the consultation, false otherwise.
     */
    public boolean hasStudent(Student student) {
        return students.contains(student);
    }

    /**
     * Returns true if both consultations have the same date, time, and students.
     *
     * @param other The other consultation to compare.
     * @return True if both consultations are the same, false otherwise.
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
     *
     * @param objects The objects to check for null values.
     * @throws NullPointerException if any object is null.
     */
    private void requireAllNonNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                throw new NullPointerException("Fields date and time must be non-null");
            }
        }
    }
}
