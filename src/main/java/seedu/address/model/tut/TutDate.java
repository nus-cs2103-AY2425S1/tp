package seedu.address.model.tut;

import seedu.address.model.student.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TutDate {

    public static final String MESSAGE_CONSTRAINTS = "Date should be in correct format (dd/mm/yyyy)!";
    private final Date date;
    private final List<Student> students;

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
