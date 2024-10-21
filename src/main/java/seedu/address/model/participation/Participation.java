package seedu.address.model.participation;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * Represents participation of a student in a tutorial
 */
public class Participation {
    private Person student;
    private Tutorial tutorial;
    private List<Attendance> attendanceList = new ArrayList<>();

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
}
