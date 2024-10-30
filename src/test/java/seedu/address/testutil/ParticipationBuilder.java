package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

/**
 * A utility class to help with building Participation objects.
 */
public class ParticipationBuilder {
    private Person student;
    private Tutorial tutorial;
    private List<Attendance> attendanceList;

    /**
     * Creates a {@code ParticipationBuilder} with the default details.
     */
    public ParticipationBuilder() {
        this.student = new PersonBuilder().build(); // Default to a new Person
        this.tutorial = new TutorialBuilder().build(); // Default to a new Tutorial
        this.attendanceList = new ArrayList<>();
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
    public ParticipationBuilder withStudent(Person student) {
        this.student = student;
        return this;
    }

    /**
     * Sets the {@code Tutorial} of the {@code Participation} that we are building.
     */
    public ParticipationBuilder withTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
        return this;
    }

    /**
     * Sets the {@code Attendance} list of the {@code Participation} that we are building.
     */
    public ParticipationBuilder withAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
        return this;
    }

    /**
     * Builds the Participation object with the current state of the builder.
     */
    public Participation build() {
        return new Participation(student, tutorial, attendanceList);
    }
}
