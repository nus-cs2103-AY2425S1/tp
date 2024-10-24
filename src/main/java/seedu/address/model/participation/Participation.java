package seedu.address.model.participation;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.person.Address.VALIDATION_REGEX;

import java.util.ArrayList;
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
    private final List<Attendance> attendanceList = new ArrayList<>();

    /**
     * Every field must be present and not null
     */
    public Participation(Person student, Tutorial tutorial) {
        requireAllNonNull(student, tutorial, attendanceList);
        this.student = student;
        this.tutorial = tutorial;
    }

    public Person getStudent() {
        return student;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }
    public String getTutorialSubject() {
        return tutorial.getSubject();
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

    /**
     * Returns true if both participation are of the same subject.
     * This defines a weaker notion of equality between two tutorials.
     */
    public boolean isSameParticipation(Participation otherParticipation) {
        if (otherParticipation == this) {
            return true;
        }
        return otherParticipation != null && otherParticipation.getTutorial().equals(getTutorial())
                                        && otherParticipation.getStudent().equals(getStudent());
    }

    @Override
    public String toString() {
        return String.format("Attends: %s", tutorial.toString());
    }
    
}
