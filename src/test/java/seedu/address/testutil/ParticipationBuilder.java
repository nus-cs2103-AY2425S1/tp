package seedu.address.testutil;

import seedu.address.model.participation.Participation;
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

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public ParticipationBuilder() {

        student = DEFAULT_STUDENT;
        tutorial = DEFAULT_TUTORIAL;
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

    public Participation build() {
        return new Participation(student, tutorial);
    }

}

