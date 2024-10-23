package seedu.address.testutil;

import seedu.address.model.PawPatrol;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building PawPatrol objects.
 * Example usage: <br>
 *     {@code PawPatrol ab = new PawPatrolBuilder().withPerson("John", "Doe").build();}
 */
public class PawPatrolBuilder {

    private PawPatrol pawPatrol;

    public PawPatrolBuilder() {
        pawPatrol = new PawPatrol();
    }

    public PawPatrolBuilder(PawPatrol pawPatrol) {
        this.pawPatrol = pawPatrol;
    }

    /**
     * Adds a new {@code Person} to the {@code PawPatrol} that we are building.
     */
    public PawPatrolBuilder withPerson(Person person) {
        pawPatrol.addPerson(person);
        return this;
    }

    public PawPatrol build() {
        return pawPatrol;
    }
}
