package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.person.Attendance;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.person.Person;

import java.util.List;

/**
 * Represents participation of a student in a tutorial
 */
public class Participation {
    private Person student;
    private Tutorial tutorial;
    private List<Attendance> attendanceList;

    public Participation(Person student, Tutorial tutorial, List<Attendance> attendanceList) {
        requireAllNonNull(student, tutorial, attendanceList);
        this.student = student;
        this.tutorial = tutorial;
        this.attendanceList = attendanceList;
    }

    public Person getStudent() {
        return student;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }
}
