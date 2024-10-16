package seedu.address.model.person.role.athlete;

import seedu.address.model.person.role.Faculty;
import seedu.address.model.person.role.Role;

/**
 * Represents an Athlete in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Athlete extends Role {
    private final Faculty faculty;
    private final Sport sport;

    /**
     * Creates an {@code Athlete} object with the given {@link Faculty} and {@link Sport}.
     *
     * @param faculty Athlete's home {@link Faculty}.
     * @param sport   Athlete's participating {@link Sport}.
     */
    public Athlete(Faculty faculty, Sport sport) {
        super("Athlete - " + faculty + " - " + SportString.getSportString(sport));
        this.faculty = faculty;
        this.sport = sport;
    }

    @Override
    public String toString() {
        return "[" + roleName + " - " + faculty + " " + SportString.getSportString(sport) + "]";
    }
}
