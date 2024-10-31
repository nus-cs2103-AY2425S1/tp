package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * A utility class to help with building Person objects.
 */
public class ParticipationBuilder {

    public static final Person DEFAULT_STUDENT = new PersonBuilder().build();
    public static final Tutorial DEFAULT_TUTORIAL = new TutorialBuilder().build();

    private Person student;
    private Tutorial tutorial;
    private List<Attendance> attendanceList;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public ParticipationBuilder() {

        student = DEFAULT_STUDENT;
        tutorial = DEFAULT_TUTORIAL;
        attendanceList = new ArrayList<>();
    }

    /**
     * Initialises the ParticipationBuilder with the data of {@code participationToCopy}.
     */
    public ParticipationBuilder(Participation participationToCopy) {
        this.student = participationToCopy.getStudent();
        this.tutorial = participationToCopy.getTutorial();
        this.attendanceList = participationToCopy.getAttendanceList();
    }

    /**
     * Sets the {@code Person} of the {@code Participation} that we are building.
     */
    public ParticipationBuilder withStudent(Person s) {
        student = s;
        return this;
    }

    /**
     * Sets the {@code Tutorial} of the {@code Participation} that we are building.
     */
    public ParticipationBuilder withTutorial(Tutorial t) {
        tutorial = t;
        return this;
    }

    /**
     * Sets the {@code Attendance} list of the {@code Participation} that we are building.
     */
    public ParticipationBuilder withAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
        return this;
    }


    public Participation build() {
        return new Participation(student, tutorial, attendanceList);
    }

}