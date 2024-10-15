package seedu.address.model.tut;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;

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

    public Date getDate() {
        return date;
    }

    public Set<StudentId> getStudentIDs() {
        return Collections.unmodifiableSet(students);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(date);
    }
}
