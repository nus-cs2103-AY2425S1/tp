package seedu.address.model.person.role.volunteer;

import seedu.address.model.person.role.Role;

/**
 * Represents a Volunteer role in the address book.
 */
public class Volunteer extends Role {
    private VolunteerRole volunteerRole;

    /**
     * Creates a Volunteer object with the given {@link VolunteerRole}.
     */
    public Volunteer(VolunteerRole volunteerRole) {
        super("Volunteer - " + volunteerRole);
        this.volunteerRole = volunteerRole;
    }
}
