package seedu.address.model.tut;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.exceptions.StudentNotInTutDateException;

/**
 * A class representing a tutorial date. Each {@code TutDate} object holds a specific {@link Date}
 * and a list of {@link Student} objects who are associated with that date.
 */
public class TutDate {

    public static final String MESSAGE_CONSTRAINTS = "Date should be in correct format (dd/mm/yyyy)!";
    private final Date date;
    private final Set<StudentId> students;

    /**
     * Constructs a {@code TutDate} object with a specified {@link Date}.
     * Initializes an empty list of {@link Student} objects for this date.
     *
     * @param date The date associated with the {@code TutDate}.
     */
    public TutDate(Date date) {
        requireNonNull(date);
        this.date = date;
        this.students = new HashSet<>();
    }

    /**
     * Add student to the hash set.
     *
     * @param student The student supposed to be added.
     */
    public void add(StudentId student) {
        requireNonNull(student);
        students.add(student);
    }

    /**
     * Removes a student from the list of students for the tutorial session.
     *
     * @param student The {@code StudentId} of the student to be removed.
     * @throws StudentNotInTutDateException if the student is not found in the list of students.
     * @throws NullPointerException if the provided {@code student} is {@code null}.
     */
    public void remove(StudentId student) {
        requireNonNull(student);
        if (!students.contains(student)) {
            throw new StudentNotInTutDateException();
        }
        students.remove(student);
    }

    public boolean isEmptyStudent() {
        return students.isEmpty();
    }

    public boolean isInTutDate(StudentId studentId) {
        return students.contains(studentId);
    }

    /**
     * Returns the date of this tutorial.
     *
     * @return the associated {@link Date}.
     */
    public Date getDate() {
        return date;
    }
    /**
     * Returns an unmodifiable list of students.
     *
     * @return an unmodifiable list of {@link Student}.
     */
    public Set<StudentId> getStudentIDs() {
        return Collections.unmodifiableSet(students);
    }

    /**
     * Returns whether the tutorial date is valid (i.e., has a non-null date).
     *
     * @return true if the date is valid, false otherwise.
     */
    public boolean isValid() {
        return date != null;
    }

    /**
     * Custom equals method to check if two TutDate objects are considered equal.
     * Two TutDate objects are equal if they have the same date and student list.
     *
     * @param other The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TutDate otherTutDate)) {
            return false;
        }

        return Objects.equals(date, otherTutDate.date) && students.equals(otherTutDate.students);
    }

    /**
     * Returns a string representation of the tutorial date.
     *
     * @return the string representation of the date.
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }

    /**
     * Generates a hash code based on the date.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
