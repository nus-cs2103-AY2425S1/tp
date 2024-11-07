package seedu.eventfulnus.model.person.role.volunteer;

import static java.util.Objects.requireNonNull;

import seedu.eventfulnus.model.person.role.Role;

/**
 * Represents a Volunteer role in the address book.
 */
public class Volunteer extends Role {
    private final VolunteerRole volunteerRole;

    /**
     * Creates a Volunteer object with the given {@link VolunteerRole}.
     */
    public Volunteer(VolunteerRole volunteerRole) {
        super("Volunteer - " + VolunteerRoleString.getVolunteerRoleString(volunteerRole));
        requireNonNull(volunteerRole);
        this.volunteerRole = volunteerRole;
    }
}
