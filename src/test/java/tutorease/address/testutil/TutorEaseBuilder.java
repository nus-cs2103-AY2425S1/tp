package tutorease.address.testutil;

import tutorease.address.model.TutorEase;
import tutorease.address.model.person.Person;

/**
 * A utility class to help with building TutorEase objects.
 * Example usage: <br>
 *     {@code TutorEase ab = new TutorEaseBuilder().withPerson("John", "Doe").build();}
 */
public class TutorEaseBuilder {

    private TutorEase tutorEase;

    public TutorEaseBuilder() {
        tutorEase = new TutorEase();
    }

    public TutorEaseBuilder(TutorEase tutorEase) {
        this.tutorEase = tutorEase;
    }

    /**
     * Adds a new {@code Person} to the {@code TutorEase} that we are building.
     */
    public TutorEaseBuilder withPerson(Person person) {
        tutorEase.addPerson(person);
        return this;
    }

    public TutorEase build() {
        return tutorEase;
    }
}
