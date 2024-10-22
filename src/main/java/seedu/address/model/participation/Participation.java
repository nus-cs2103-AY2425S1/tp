package seedu.address.model.participation;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.person.Address.VALIDATION_REGEX;

import java.util.List;

import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Represents participation of a student in a tutorial
 */
public class Participation {
    private final Person student;
    private final Tutorial tutorial;
    private final List<Attendance> attendanceList;

    /**
     * Every field must be present and not null
     */
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
    public static Boolean isValidParticipationList(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * ensures the immutability of the class
     * @return a new List of attendance
     */
    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    @Override
    public String toString() {
        return String.format("Attends: %s", tutorial.toString());
    }

    /**
     * Checks if this participation is for the given tutorial
     */
    public boolean isParticipationOfTutorial(String tutorial) {
        return this.tutorial.getSubject().equals(tutorial);
    }
}
