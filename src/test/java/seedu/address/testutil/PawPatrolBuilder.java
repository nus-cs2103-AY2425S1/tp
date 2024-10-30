package seedu.address.testutil;

import seedu.address.model.PawPatrol;
import seedu.address.model.owner.Owner;

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
     * Adds a new {@code Owner} to the {@code PawPatrol} that we are building.
     */
    public PawPatrolBuilder withOwner(Owner owner) {
        pawPatrol.addOwner(owner);
        return this;
    }

    public PawPatrol build() {
        return pawPatrol;
    }
}
