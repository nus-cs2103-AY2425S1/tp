package seedu.address.model.tut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import seedu.address.model.student.Student;

/**
 * A class representing a tutorial date. Each {@code TutDate} object holds a specific {@link Date}
 * and a list of {@link Student} objects who are associated with that date.
 */
public class TutDate {

    public static final String MESSAGE_CONSTRAINTS = "Date should be in correct format (dd/mm/yyyy)!";
    private final Date date;
    private final List<Student> students;

    /**
     * Constructs a {@code TutDate} object with a specified {@link Date}.
     * Initializes an empty list of {@link Student} objects for this date.
     *
     * @param date The date associated with the {@code TutDate}.
     */
    public TutDate(Date date) {
        this.date = date;
        this.students = new ArrayList<>();
    }

    public void add(Student student) {
        students.add(student);
    }

    public Date getDate() {
        return date;
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    @Override
    public String toString() {
        return date.toString();
    }
}
