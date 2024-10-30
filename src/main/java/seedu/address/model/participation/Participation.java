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
    private final List<Attendance> attendanceList;

    /**
     * Person and Tutorial must be present and not null
     */
    public Participation(Person student, Tutorial tutorial) {
        requireAllNonNull(student, tutorial);
        this.student = student;
        this.tutorial = tutorial;
        this.attendanceList = new ArrayList<>();
    }

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
     * This defines a weaker notion of equality between two participations.
     */
    public boolean isSameParticipation(Participation otherParticipation) {
        if (otherParticipation == this) {
            return true;
        }
        return otherParticipation != null && otherParticipation.getTutorial().equals(getTutorial())
                                        && otherParticipation.getStudent().equals(getStudent());
    }

    /**
     * Adds itself to the student and tutorial participation lists
     */
    public void addSelfToStudentTutorial() {
        student.addParticipation(this);
        tutorial.addParticipation(this);
    }

    @Override
    public String toString() {
        return String.format("%s Attends: %s", student.toString(), tutorial.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Participation)) {
            return false;
        }

        Participation otherParticipation = (Participation) other;
        return this.student.equals(otherParticipation.student)
                && this.tutorial.equals(otherParticipation.tutorial);
    }

}
